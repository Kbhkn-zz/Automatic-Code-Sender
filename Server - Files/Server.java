import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

//-------------------------------------------------------------------------------------------------------------------
//YKB Staj için verilen Proje.
//Application Sunucularında ani bir durum çıktığında birden fazla sunucuda aynı dizin altında bulunan
//logları vs. incelemek gerekiyor. Bu masaüstü uygulamasıda birden fazla sunucuya aynı komutları eş zamanlı göndermeyi
//ve sonuçlarını ekrana hızlı bir biçimde ve düzenli bir şekilde dökme işlemine yarıyor.
//Socket -- Process -- Thread mantığını bildiğim kadarıyla projeyi tasarladım.
//--------------------------------------------------------------------------------------------------------------------


public class Server {

	private static ServerSocket server;
	private static String com, line, comlist = "", donus = "";
	private static BufferedReader br;
	private static FileWriter log;
	private static Runtime runtime = Runtime.getRuntime();
	private static SimpleDateFormat date;

	public void server() throws IOException {
		date = new SimpleDateFormat("dd/M/yyyy-HH:mm:ss");//Tarih için biçim belirlendi.
		server = new ServerSocket(20000);//20000 portu ile LISTEN modu başlatıldı. 
		try {
			while (true) {
				final Socket client = server.accept();//Client bekleme işlemi başlatıldı.
				//Gelen komutlara cevap vermek için xxx değişkeni kullanıldı.
				final DataOutputStream sendCodeOutput = new DataOutputStream(client.getOutputStream());
				//Gelen komutlar alınmak için xxx değişkeni kullanıldı.
				final DataInputStream readCommand = new DataInputStream(client.getInputStream());
				//Gelen komut UTF biçiminde okuntu.
				final String readMessage = readCommand.readUTF();
				//Komutlar SHELL ile çalıştırıldı.
				final String[] commd1 = { "/bin/sh", "-c", readMessage + " > CodeOutput.out" };
				final String[] commd2 = { "/bin/sh", "-c", "cat CodeOutput.out" };
				final String[] commd3 = { "/bin/sh", "-c", "rm CodeOutput.out" };

				//Eğer Komut listesi isteniyorsa komut listesinin bulunduğu dosya Client'e yollandı.
				if (readMessage.equals("UpdateCommandList")) {
					try {
						br = new BufferedReader(new FileReader("//home//kbhkn//CommandList"));
						while ((com = br.readLine()) != null) {
							comlist += com + "\n";
						}
						sendCodeOutput.writeUTF(comlist);
					} catch (final Exception ex) {
						sendCodeOutput.writeUTF("List could not be updated!\n" + ex);
					} finally {
						comlist = "";
						br.close();//En son dosya kapatıldı.
						client.close();//Client ile olan bağ kapatıldı.
					}
				} else {
					try {
						final Process p = runtime.exec(commd1);//Gelen komut çalıştırıldı.
						//Normalde Thread.sleep komutuna ihtiyacım yoktu ancak Dinamik çıktı üreten komutlarda
						//Bu kodu kullanmak zorunda kaldım. Çünkü benim yazdığım kod gelen kodu oku çalıştır cevap ver
						//Şeklinde tasarlanmış. Ancak Dinamik sonuç üreten kodlar sadece CTRL+C ile kapatıldığı için
						//Bende gelen her türlü kodu çalıştırıp o anki Thread'i 100 milisaniye uyutup elinde barındırdığı
						//veriyi hafızaya attım ve bu Processi kill ettim.Bu şekilde Dynamic kod Static koda dönüşmüş oldu.
						Thread.sleep(100);
						p.destroy();

						final Process p2 = runtime.exec(commd2);
						final BufferedReader br = new BufferedReader(new InputStreamReader(p2.getInputStream()));
						//Çalıştırılan komut dosyaya yazılıp dosyadan okuma işlemi yapılarak hafızaya alındı.
						try {
							log = new FileWriter("//home//kbhkn//Command_History_List", true);
							//İleride kullanılan bir kod sıkıntı çıkartırsa bu hangi kod ?
							//Kim bu kodu gönderme gereği duymuş ?
							//Hangi tarih ? Hangi Saatte? gibi sorulara cevap vermek için loglama işlemi yapıldı.
							log.write(date.format(new Date()) + " IP: " + client.getInetAddress() + " => " + readMessage + "\n");

						} catch (final Exception ex) {
							System.out.println("Log kaydı tutulamadı!");
						} finally {
							log.close();
						}

						try {
							//Hafızaya alınan kodun çıktısı satır satır okunarak Client'e yollandı.
							while ((line = br.readLine()) != null) {
								donus += line + "\n";
								if (donus.length() > 40000) {
									break;
								}
							}
							//Eğer komut çalıştırılamayısa boyut 0 olacağı için farklı bir uyarı yollandı.
							sendCodeOutput.writeUTF(donus.length() != 0 ? donus : "Command could not be executed!");
						} catch (final IOException ex) {
							sendCodeOutput.writeUTF(donus);
						} finally {
							br.close();//Açılan herşeyi kapatmak lazım :)
							final Process p3 = runtime.exec(commd3);
							if (p3.waitFor() == 0)
								//İşlem bittikten sonra namepiped mantığını manuel olarak kullandım.
								p3.destroy();
						}
					} catch (final Exception ex) {
						sendCodeOutput.writeUTF("Command could not be executed!\n" + ex);
					} finally {
						donus = "";
						line = null;
						client.close();
					}
				}
			}
		} catch (final Exception ex) {
			System.out.println("Error: " + ex + "\nConnection Fails!");
		}
	}

	public static void main(final String[] args) throws IOException {
		final Server s1 = new Server();

		try {
			s1.server();
		} catch (final Exception ex) {
			System.out.println("Server could not be started!\n" + ex);
		}
	}
}