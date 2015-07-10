package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

//XXX
public class AutomaticCodeSenderController implements Initializable {
	@FXML
	private ComboBox<String> combobox;// Komut listesi için combobox kullanıldı.

	@FXML
	private ComboBox<Integer> timebox;// Auto Send için timer comboboxı kullanıldı.

	@FXML
	private Integer ctime;

	@FXML
	private String command;

	@FXML
	private Label label;

	@FXML
	private Timer myTimer;

	@FXML
	private TimerTask myTask;

	@FXML
	private CheckBox server1, server2, server3, server4, server5;

	@FXML
	private Socket soket = null, soket2 = null, soket3 = null, soket4 = null, soket5 = null;

	@FXML
	private Button clear, disconnect, send, update, autosend, stoptimer;

	@FXML
	public TextArea firstarea, secoundarea, thirdarea, fourtharea, fiftharea;

	@FXML
	private Tab first, secound, third, fourth, fifth;

	// Sunucudan gönderilebilicek komutları almak için boş komut listesi
	// yaratıldı.
	ObservableList<String> list = FXCollections.observableArrayList();
	// Otomatik kod göndermek için zamanlayıcı listesi.
	ObservableList<Integer> list2 = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

	// Sekmeler CheckBox'a göre aktif kapalı duruma alınıyor.
	@FXML
	private void handleButtonAction(final ActionEvent e) {
		first.setDisable(!server1.isSelected());
		secound.setDisable(!server2.isSelected());
		third.setDisable(!server3.isSelected());
		fourth.setDisable(!server4.isSelected());
		fifth.setDisable(!server5.isSelected());
		if (!(server1.isSelected() || server2.isSelected() || server3.isSelected() || server4.isSelected()
				|| server5.isSelected())) {
			send.setDisable(true);
			autosend.setDisable(true);
		} else {
			if (combobox.getValue() != null) {
				send.setDisable(false);
				autosend.setDisable(false);
			}
		}
	}

	// Kod çıktıları listelenen Sekmelerin tümü temizleniyor.
	@FXML
	private void clear(final ActionEvent event) {
		try {
			firstarea.setText("");
			secoundarea.setText("");
			secoundarea.setText("");
			fourtharea.setText("");
			fiftharea.setText("");
			label.setText("Command history has been cleared.");
		} catch (final Exception ex) {
			label.setText("Command history failed to clean!");
		}
	}

	// Komutları kullanabilmek için sunucudan komut listesi alınıyor.
	@FXML
	private void updateCommandList() throws IOException {

		try {
			soket = new Socket("192.168.201.210", 20000);//Komutların çekileceği server'a bağlan.
		} catch (Exception ex) {
			label.setText("Connection failed\n" + ex);
		}
		if (soket.isConnected()) {
			try {
				final DataOutputStream csend = new DataOutputStream(soket.getOutputStream());
				csend.writeUTF("UpdateCommandList");

				final DataInputStream cupdate = new DataInputStream(soket.getInputStream());
				final String data = cupdate.readUTF();
				try {
					combobox.getItems().clear();// Komut listesinin temizlenmesi.
					final String[] commands = data.split(";");
					for (int i = 0; i < commands.length - 1; i++) {
						combobox.getItems().add(commands[i].trim());
						// Kullanılabilecek komutlar yükleniyor.
					}
					// Komutlar yüklendikten sonra komut çalıştırmak için program aktifleştiriliyor.
					server1.setDisable(false);
					server2.setDisable(false);
					server3.setDisable(false);
					server4.setDisable(false);
					server5.setDisable(false);
					clear.setDisable(false);
					disconnect.setDisable(false);
					label.setText("Command list was updated.");
				} catch (final Exception ex) {
					label.setText("Command list failed to update!\n" + ex);
				}

			} catch (final Exception exp) {
				label.setText("Command list failed to update\n" + exp);
			} finally {
				soket.close();
			}
		}
	}

	// Komut göndermek için buton yapılandırıldı.
	@FXML
	private void buttonAction() throws UnknownHostException, IOException, InterruptedException {
	//FIXME Bazen soket bağlanmaya çalışırken bağlanamadı demiyorda program donuyor :(
		if ((command = combobox.getValue()) == null) {
			// Eğer herhangi bir komut seçilmediyse birşey yollama.
			label.setText("Please choose a command!");
		} else {
			label.setText("Sending command: " + command);
			if (server1.isSelected()) {
				try {
					soket = new Socket("192.168.201.210", 20000);
					firstServer();
				} catch (final Exception ex) {
					firstarea.setText("Connection failed!\n" + ex);
				}
			}

			if (server2.isSelected()) {
				try {
					soket2 = new Socket("10.50.12.59", 20000);
					secoundServer();
				} catch (final Exception ex) {
					secoundarea.setText("Connection failed!\n" + ex);
				}
			}
			if (server3.isSelected()) {
				try {
					soket3 = new Socket("10.50.12.61", 20000);
					thirdServer();
				} catch (final Exception ex) {
					thirdarea.setText("Connection failed!\n" + ex);
				}
			}
			if (server4.isSelected()) {
				try {
					soket4 = new Socket("10.50.12.68", 20000);
					fourthServer();
				} catch (final Exception ex) {
					fourtharea.setText("Connection failed!\n" + ex);
				}
			}

			if (server5.isSelected()) {
				try {
					soket5 = new Socket("10.50.12.69", 20000);
					fifthClient();
				} catch (final Exception ex) {
					fiftharea.setText("Connection failed!\n" + ex);
				}
			}

			else if (!(server1.isSelected() || server2.isSelected() || server3.isSelected() || server4.isSelected()
					|| server5.isSelected())) {
				label.setText("Please, choose a server.");
			}
		}
	}

	// Disconnect butonuna tıklanıldığı zaman tüm aktif bağlantıları kapat.
	@FXML
	private void Disconnet() {
		try {
			if (soket != null)
				soket.close();
			if (soket2 != null)
				soket2.close();
			if (soket3 != null)
				soket3.close();
			if (soket4 != null)
				soket4.close();
			if (soket5 != null)
				soket5.close();
			label.setText("Connection was successfully closed!");
		} catch (final Exception ex) {
			label.setText("Connection not active or failed to close!" + ex);
		} finally {
			if (!stoptimer.isDisabled()) {
				// Eğer otomatik kod gönderme kapatılmadıysa bunuda kapat.
				myTimer.cancel();
				stoptimer.setDisable(true);
			}
			// Tekrar komut gönderilmesi için butonlar tekrardan
			// aktifleştirildi.
			combobox.setDisable(false);
			timebox.setDisable(false);
			send.setDisable(false);
			update.setDisable(false);
			autosend.setDisable(false);
		}
	}

	// Otomatik kod göndermek için zamanlayıcının yaratılması ve
	// konfigürasyonların yapılması.
	@FXML
	public void autoSend() throws UnknownHostException, IOException, InterruptedException {
		if ((ctime = timebox.getValue()) != null) {
			// Otomatik kod göndermek için süre seçildiyse zamanlayıcı yarat.
			myTimer = new Timer();
			myTask = new TimerTask() {

				@Override
				public void run() {
					Platform.runLater(() -> {
						// Thread'ı GUI arayüzünde aktifleştir.
						try {
							buttonAction();
						} catch (IOException | InterruptedException e) {
							label.setText("Error! Please try again!");
						}
					});
				}
			};

			myTimer.schedule(myTask, 0, ctime * 1000);
			// Seçilen süre boyunca zamanlayıcı başlat.
			// Otomatik kod gönderme başladığı zaman BUG çıkmaması için Buton
			// vs. devre dışı bırak.
			stoptimer.setDisable(false);
			combobox.setDisable(true);
			timebox.setDisable(true);
			send.setDisable(true);
			update.setDisable(true);
			autosend.setDisable(true);
		} else {
			label.setText("Please, choose a time!");
		}
	}

	// Otomatik kod gönderme işlemi durdurulmak isteniyorsa Timer iptal et ve
	// programı tekrar aktifleştir.
	@FXML
	public void timerStop() {
		try {
			myTimer.cancel();
			stoptimer.setDisable(true);
			combobox.setDisable(false);
			timebox.setDisable(false);
			send.setDisable(false);
			update.setDisable(false);
			autosend.setDisable(false);
		} catch (final Exception ex) {
			label.setText("Auto Timer failed to close!");
		}
	}

	// 1.sunucuya bağlanılmak isteniliyorsa gerekli işlevlerin yapılması.
	public void firstServer() throws UnknownHostException, IOException {
		try {
			// Server1'e bağlanılmak isteniliyorsa seçilen komutu yolla.
			final DataOutputStream sendcommandserver1 = new DataOutputStream(soket.getOutputStream());
			sendcommandserver1.writeUTF(command);
			// Server1'de işlenip gelen komutun çıktısını birinci sekmeye dök.
			final DataInputStream outputserver1 = new DataInputStream(soket.getInputStream());
			firstarea.setText(outputserver1.readUTF());

		} catch (final Exception ex) {
			firstarea.setText("Server 1, Connection failed!" + ex);
		} finally {
			firstarea.setScrollTop(Double.MAX_VALUE);// Her zaman sütunu en
														// aşağı indir.
			soket.close();
		}
	}

	// 2.sunucuya bağlanılmak isteniliyorsa gerekli işlevlerin yapılması.
	public void secoundServer() throws UnknownHostException, IOException {
		try {
			final DataOutputStream sendcommandserver2 = new DataOutputStream(soket2.getOutputStream());
			sendcommandserver2.writeUTF(command);

			final DataInputStream outputserver2 = new DataInputStream(soket2.getInputStream());
			secoundarea.setText(outputserver2.readUTF());
		} catch (final Exception ex) {
			secoundarea.setText("Server2, Connection failed!");
		} finally {
			secoundarea.setScrollTop(Double.MAX_VALUE);
			soket2.close();
		}
	}

	// 3.sunucuya bağlanılmak isteniliyorsa gerekli işlevlerin yapılması.
	public void thirdServer() throws UnknownHostException, IOException {
		try {
			final DataOutputStream sendcommandserver3 = new DataOutputStream(soket3.getOutputStream());
			sendcommandserver3.writeUTF(command);

			final DataInputStream outputserver3 = new DataInputStream(soket3.getInputStream());
			thirdarea.setText(outputserver3.readUTF());
		} catch (final Exception ex) {
			thirdarea.setText("Server3, Connection failed!");
		} finally {
			thirdarea.setScrollTop(Double.MAX_VALUE);
			soket3.close();
		}
	}

	// 4.sunucuya bağlanılmak isteniliyorsa gerekli işlevlerin yapılması.
	public void fourthServer() throws UnknownHostException, IOException {
		try {
			final DataOutputStream sendcommandserver4 = new DataOutputStream(soket4.getOutputStream());
			sendcommandserver4.writeUTF(command);

			final DataInputStream outputserver4 = new DataInputStream(soket4.getInputStream());
			fourtharea.setText(outputserver4.readUTF());
		} catch (final Exception ex) {
			fourtharea.setText("Server4, Connection failed!");
		} finally {
			fourtharea.setScrollTop(Double.MAX_VALUE);
			soket4.close();
		}
	}

	// 5.sunucuya bağlanılmak isteniliyorsa gerekli işlevlerin yapılması.
	public void fifthClient() throws UnknownHostException, IOException {
		try {
			final DataOutputStream sendcommandserver5 = new DataOutputStream(soket5.getOutputStream());
			sendcommandserver5.writeUTF(command);

			final DataInputStream outputserver5 = new DataInputStream(soket5.getInputStream());
			fiftharea.setText(outputserver5.readUTF());
		} catch (final Exception ex) {
			fiftharea.setText("Server5, Connection failed!");
		} finally {
			fiftharea.setScrollTop(Double.MAX_VALUE);
			soket5.close();
		}
	}

	// Controller için initialize kısmı program ilk çalıştırıldığı anda Süreyi
	// ve
	// Boş komut listesini yükle.
	public void initialize(final URL location, final ResourceBundle resources) {
		combobox.setItems(list);
		timebox.setItems(list2);
	}
}