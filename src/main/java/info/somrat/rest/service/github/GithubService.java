package info.somrat.rest.service.github;

import info.somrat.rest.dto.GithubUserDTO;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GithubService {
    CompletableFuture<List<GithubUserDTO>> getGitHubUsers();
}
