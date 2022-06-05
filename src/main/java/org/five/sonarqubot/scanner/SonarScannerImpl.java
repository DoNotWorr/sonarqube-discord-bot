package org.five.sonarqubot.scanner;

import org.sonarsource.scanner.api.EmbeddedScanner;
import org.sonarsource.scanner.api.StdOutLogOutput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * Scans code files with Sonar Scanner API.<br><br>
 * More information about Sonar Scanner API on their <a href="https://github.com/SonarSource/sonar-scanner-api">Github</a>.<br>
 * More information about available analysis parameters for Sonar Scanner in <a href="https://docs.sonarqube.org/latest/analysis/analysis-parameters/">SonarQube documentation</a> .
 */
@Component
public class
SonarScannerImpl implements SonarScanner {
    @Value("${sonar.host.url}")
    private String SONAR_HOST_URL;

    @Value("${sonar.scanner.code.folder}")
    private String CODE_FOLDER;

    @Value("${sonar.scanner.name}")
    private String SCANNER_NAME;

    /**
     * Scan a file and send result to SonarQube server.
     *
     * @param projectKey   Sonar project key.
     * @param projectToken Sonar project token.
     * @param fileName     file name with extension (like "example_file.java"). Note that empty or non-existent files will be yield a successful analysis.
     * @return {@link reactor.core.publisher.Mono} that completes when scan is finished or terminates on exceptions. Mono may complete before server has analysed the result.
     */
    @Override
    public Mono<Void> scan(String projectKey, String projectToken, String fileName) {
        try {
            EmbeddedScanner embeddedScanner = create(projectKey, projectToken, fileName);
            embeddedScanner.start();
            embeddedScanner.execute(new HashMap<>());
            return Mono.empty();
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    private EmbeddedScanner create(String projectKey, String projectToken, String fileName) {
        return EmbeddedScanner.create(SCANNER_NAME, "not provided", new StdOutLogOutput())
                .setGlobalProperty("sonar.scm.exclusions.disabled", "true")
                .setGlobalProperty("sonar.host.url", SONAR_HOST_URL)
                .setGlobalProperty("sonar.sources", CODE_FOLDER)
                .setGlobalProperty("sonar.projectKey", projectKey)
                .setGlobalProperty("sonar.login", projectToken)
                .setGlobalProperty("sonar.inclusions", "**/*" + fileName);
    }
}
