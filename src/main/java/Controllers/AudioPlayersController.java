package Controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class AudioPlayersController {
	private static final String AUDIO_PATH ="/home/codeman/Code/Java/MusicLocalService/src/main/resources/sounds/";
	private static final int BYTE_RANGE = 128;
	@GetMapping("/songs/audio/{filename}")
	public Mono<ResponseEntity<byte[]>> streamAudio( 
		@RequestParam(value = "Range", required = false)
		String httpRangeList,
		@PathVariable(name = "filename")
		String fileName 
	)
	{ return Mono.just(getContent(AUDIO_PATH, fileName, httpRangeList, "audio")); }

	private ResponseEntity<byte[]> getContent(String audioPath, String fileName, String httpRangeList, String string) {
		long rangeStart = 0;
		long rangeEnd = 0;
		byte[] data = null;
		Long fileSize;
		String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
		try {
			fileSize = Optional
					.ofNullable(fileName)
					.map( file -> Path.of( audioPath.toString(),file))
					.map(this::sizeFromFile)
					.orElse(0L);
			if (httpRangeList  == null) {
		           return ResponseEntity.status(HttpStatus.OK)
		                 .header("Content-Type", string + "/" + fileType)
		                 .header("Content-Length", String.valueOf(fileSize))	
		                 .body(readByteRange(audioPath, fileName, rangeStart, fileSize - 1));	
			}	
			
		}catch( IOException e ){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);

	     return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
	           .header("Content-Type", string +"/" + fileType)
	           .header("Accept-Ranges", "bytes")
	           .header("Content-Length", contentLength)
	           .header("Content-Range", "bytes" + " " + rangeStart + "-" + rangeEnd + "/" + fileSize)
	           .body(data);
	}
	private byte[] readByteRange(String audioPath, String fileName, long rangeStart, long rangeEnd) throws IOException {
	     Path path = Path.of(audioPath, fileName);
	     try (InputStream inputStream = (Files.newInputStream(path));
	         ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream()) {
	        byte[] data = new byte[BYTE_RANGE];
	        int nRead;
	        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
	           bufferedOutputStream.write(data, 0, nRead);
	        }
	        bufferedOutputStream.flush();
	        byte[] result = new byte[(int) (rangeEnd - rangeStart) + 1];
	        System.arraycopy(bufferedOutputStream.toByteArray(), (int) rangeStart, result, 0, result.length);
	        return result;
	     }	
	}
	private  Long sizeFromFile(Path path) {
		try { return Files.size(path); }
		catch(IOException e) {
			e.printStackTrace();
		}
		return 0L;
	}
}
