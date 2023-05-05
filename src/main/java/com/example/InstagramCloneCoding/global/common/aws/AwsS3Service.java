package com.example.InstagramCloneCoding.global.common.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.InstagramCloneCoding.domain.member.error.MemberErrorCode.IMAGE_UPLOAD_FAIL;
import static com.example.InstagramCloneCoding.global.error.CommonErrorCode.ONLY_IMAGE_ALLOWED;

@Service
@Transactional
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> uploadFile(List<MultipartFile> multipartFiles) {
        List<String> fileNameList = new ArrayList<>();

        multipartFiles.forEach(file -> {
            validateContentType(file);
            String s3FileName = UUID.randomUUID().toString(); // 이름 중복 피하기

            try {
                ObjectMetadata objMeta = new ObjectMetadata();
                objMeta.setContentLength(file.getInputStream().available());
                objMeta.setContentType(file.getContentType());

                // s3 버킷에 업로드
                amazonS3.putObject(
                        new PutObjectRequest(bucket, s3FileName, file.getInputStream(), objMeta)
                                .withCannedAcl(CannedAccessControlList.PublicRead) // 업로드되는 파일에 public read 권한 부여
                );
                file.getInputStream().close();
            } catch (Exception e) {
                throw new RestApiException(IMAGE_UPLOAD_FAIL);
            }

            fileNameList.add(amazonS3.getUrl(bucket, s3FileName).toString());
        });

        return fileNameList;
    }

    public void deleteFile(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName.substring(59)));
    }

    // 파일 확장자 검사 (jpg, png 만 가능)
    private void validateContentType(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        System.out.println(contentType);
        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            throw new RestApiException(ONLY_IMAGE_ALLOWED);
        }
    }
}
