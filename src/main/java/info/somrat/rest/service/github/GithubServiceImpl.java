package info.somrat.rest.service.github;

import info.somrat.rest.dto.GithubUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class GithubServiceImpl implements GithubService {
    private static final Logger logger = LoggerFactory.getLogger(GithubServiceImpl.class);

    private final RestTemplate restTemplate;

    public GithubServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<GithubUserDTO> getGitHubUserAsync(String username) {
        String apiUrl = "https://api.chucknorris.io/jokes/random";
        try {
            GithubUserDTO gitHubUser = restTemplate.getForObject(apiUrl, GithubUserDTO.class);
            logger.info("successfully getting the result for username: " + username);
            return CompletableFuture.completedFuture(gitHubUser);
        } catch (Exception e) {
            logger.error("Error when fetching username: " + username);
            return new CompletableFuture<>();
        }
    }

    @Async
    @Override
    public CompletableFuture<List<GithubUserDTO>> getGitHubUsers() {
        List<String> usernames = Arrays.asList("PivotalSoftware", "CloudFoundry", "Spring-Projects", "RameshMF");
        List<CompletableFuture<GithubUserDTO>> userFutures = new ArrayList<>();
        for (String username : usernames) {
            CompletableFuture<GithubUserDTO> userFuture = this.getGitHubUserAsync(username);
            userFutures.add(userFuture);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(userFutures.toArray(new CompletableFuture[0]));
        return allOf.thenApply(voidResult -> userFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));
    }

}
