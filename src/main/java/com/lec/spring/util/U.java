package com.lec.spring.util;

import com.lec.spring.domain.PartnerMenu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class U {

    // 이미지 파일의 확장자를 추출하는 메서드
    public static Optional<String> getExtensionByStringHandling(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return Optional.empty();
        }
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex == -1) {
            return Optional.empty();
        }
        return Optional.of(fileName.substring(lastIndex + 1));
    }

    // 이미지 파일의 확장자가 유효한지 확인하는 메서드
    public static boolean isValidImageFileExtension(String extension) {
        // 여기에 이미지 파일의 확장자 목록을 추가하면 됩니다.
        // 예를 들어, "jpg", "jpeg", "png", "gif" 등을 포함할 수 있습니다.
        // 이 목록은 프로젝트의 요구에 따라 적절하게 조정되어야 합니다.
        // 여기서는 간단하게 "jpg"와 "png" 확장자만을 허용하도록 작성하겠습니다.
        return extension != null && (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png"));
    }

    // 파일에 고유한 이름을 생성하는 메서드
    public static String generateUniqueFileName(String fileName) {
        // 파일 이름을 고유하게 만드는 데에는 다양한 방법이 있습니다.
        // 여기서는 UUID를 사용하여 고유한 이름을 생성하겠습니다.
        String extension = getExtensionByStringHandling(fileName).orElse("");
        return UUID.randomUUID().toString() + "." + extension;
    }

    public static String setImagePath(String uploadDir, String uniqueFileName) {
        return uploadDir + File.separator + uniqueFileName;
    }

    // 이미지 파일 확장자 목록
    private static final String[] IMAGE_EXTENSIONS = {"jpg", "jpeg", "png", "gif"};

    public static boolean isImageFile(MultipartFile file) {
        String extension = getFileExtension(file.getOriginalFilename());
        for (String ext : IMAGE_EXTENSIONS) {
            if (ext.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    private static String getFileExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int extensionIndex = filename.lastIndexOf('.');
        if (extensionIndex == -1) {
            return null;
        }
        return filename.substring(extensionIndex + 1);
    }

    public static void printFileInfo(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();   // 원본 이름

        if(originalFileName == null || originalFileName.length() == 0){
            System.out.println("\t파일이 없습니다");
            return;
        }

        System.out.println("\tOriginal File Name : " + originalFileName);
        System.out.println("\tCleanPath : " + StringUtils.cleanPath(originalFileName));
        System.out.println("\tFile Size : " + file.getSize() + " bytes");  // 용량 (byte)
        System.out.println("\tMIME: " + file.getContentType());  // content type (mime type)

        // 이미지 파일 여부
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(file.getInputStream());

            if(bufferedImage != null){
                System.out.printf("\t이미지 파일입니다: %d x %d\n", bufferedImage.getWidth(), bufferedImage.getHeight());
            } else {
                System.out.println("\t이미지 파일이 아닙니다");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
