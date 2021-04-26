package compasso.estagio.grupo.projeto5.Telas.AmazonS3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class FileSaverService {

	@Value("${application.bucket.name}")
	private String bucket;

	@Autowired
	private AmazonS3 clienteS3;

	public void uploadFile(MultipartFile MultiFile) {
		File file = convertMultiPartFileToFile(MultiFile);
		clienteS3.putObject(new PutObjectRequest(bucket, MultiFile.getOriginalFilename(), file));
		file.delete();
	}


	private File convertMultiPartFileToFile(MultipartFile file) {
		File convertedFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
			fos.write(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return convertedFile;
	}

}