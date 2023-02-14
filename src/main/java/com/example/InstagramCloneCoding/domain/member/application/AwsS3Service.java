package com.example.InstagramCloneCoding.domain.member.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.example.InstagramCloneCoding.domain.member.error.MemberErrorCode.IMAGE_UPLOAD_FAIL;

@Service
@Transactional
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile) {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename(); // 이름 중복 피하기

        try {
            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(multipartFile.getInputStream().available());

            // s3 버킷에 업로드
            amazonS3.putObject(
                    new PutObjectRequest(bucket, s3FileName, multipartFile.getInputStream(), objMeta)
                            .withCannedAcl(CannedAccessControlList.PublicRead) // 업로드되는 파일에 public read 권한 부여
            );
        } catch (Exception e) {
            throw new RestApiException(IMAGE_UPLOAD_FAIL);
        }

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }
}
