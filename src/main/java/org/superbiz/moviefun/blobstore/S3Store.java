package org.superbiz.moviefun.blobstore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


public class S3Store implements BlobStore {

    private AmazonS3Client amazonS3Client;
    private String photoStorageBucket;

    public S3Store(AmazonS3Client s3Client, String photoStorageBucket) {
            this.amazonS3Client = s3Client;
            this.photoStorageBucket = photoStorageBucket;
    }

    @Override
    public void put(Blob blob) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(blob.contentType);
        PutObjectRequest objectRequest = new PutObjectRequest(photoStorageBucket, blob.name, blob.inputStream,objectMetadata);
        amazonS3Client.putObject(objectRequest);
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        Blob blob = null;
        try {
            S3Object s3Object = amazonS3Client.getObject(photoStorageBucket, name);
            blob = new Blob(name, s3Object.getObjectContent(), s3Object.getObjectMetadata().getContentType());
        } catch (AmazonServiceException e) {
           System.out.println(String.format("Pas d'image name:%s", name));
        }
        return Optional.ofNullable(blob);
    }

    @Override
    public void deleteAll() {

    }
}
