package org.egeiper.util;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;

public final class S3Utils {

    private S3Utils() {
    }

    public static String getObject(final String bucketName, final String key, final String profileName, Region region) throws IOException {
        S3Client s3 = S3Client.builder().credentialsProvider(ProfileCredentialsProvider.create(profileName))
                .region(region).build();
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();
        ResponseInputStream<GetObjectResponse> response = s3.getObject(getObjectRequest);
        return new String(response.readAllBytes());
    }
}