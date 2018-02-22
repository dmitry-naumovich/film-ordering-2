package by.epam.naumovich.film_ordering.service.util;

import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.FileNotUploadedException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;


public class FileUploader {

    public static void writeFileItem(HttpSession session, int entityId, FileItem item, String fileName, String uploadDir)
            throws FileNotUploadedException, IOException {
        if (item != null) {
            String absoluteFilePath = session.getServletContext().getRealPath(uploadDir);

            String concreteFileName = new File(fileName).getName();
            File image = new File(absoluteFilePath, concreteFileName);

            try {
                item.write(image);
            } catch (Exception e) {
                throw new FileNotUploadedException(ErrorMessages.FILE_NOT_UPLOADED, e);
            }

            String absTargetFilePath = session.getServletContext().getRealPath(uploadDir + entityId + "/");
            File targetImg = new File(absTargetFilePath, concreteFileName);

            Files.move(image.toPath(), targetImg.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static void loadImage(HttpSession session, FileItem item, String fileName, String uploadDir, boolean createDir)
            throws FileNotUploadedException {

        if (item != null) {
            String absoluteFilePath = session.getServletContext().getRealPath(uploadDir);
            if (createDir && absoluteFilePath != null) {
                new File(absoluteFilePath).mkdir();
            }

            String concreteFileName = new File(fileName).getName();

            try {
                item.write(new File(absoluteFilePath, concreteFileName));
            } catch (Exception e) {
                throw new FileNotUploadedException(ErrorMessages.FILE_NOT_UPLOADED, e);
            }
        }
    }
}
