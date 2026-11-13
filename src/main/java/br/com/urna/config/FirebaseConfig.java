package br.com.urna.config;

import br.com.urna.repository.FirestoreRepository;
import br.com.urna.repository.Repository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.util.Optional;

public final class FirebaseConfig {
    private FirebaseConfig() {}

    public static Optional<Repository> createRepository() {
        String credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        if (credentialsPath == null || credentialsPath.isBlank()) return Optional.empty();
        try (FileInputStream credentials = new FileInputStream(credentialsPath)) {
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(credentials)).build();
                FirebaseApp.initializeApp(options);
            }
            return Optional.of(new FirestoreRepository(FirestoreClient.getFirestore()));
        } catch (Exception e) {
            System.err.println("Firebase indisponível: " + e.getMessage());
            return Optional.empty();
        }
    }
}
