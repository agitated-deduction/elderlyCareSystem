
//맨 위
import java.io.OutputStream;



else if(tp[2].equals("vid")) {
				//"home/{num}/vid"
				FileOutputStream foutput = new FileOutputStream("D:/Eclipse/Java/Output.avi");

				byte[] vid = message.getBytes();
				Decoder decoder = Base64.getDecoder();
				
				byte[] dvid = decoder.decode(vid);
				// SimpleDateFormat forma = new SimpleDateFormat("yyyyMMdd");
				// Date time = new Date();
				
				foutput.write(dvid)
				myAlert(tp[2]);
			}