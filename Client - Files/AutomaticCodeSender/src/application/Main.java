package application;

//-----------------------------------------------------------------------------------------------------------------
//Staj için verilen bir projedir.
//Application Sunucularında ani bir durum çıktığında birden fazla sunucuda aynı dizin altında bulunan
//logları vs. incelemek gerekiyor. Bu masaüstü uygulamasıda birden fazla sunucuya aynı komutları eş zamanlı göndermeyi
//ve sonuçlarını ekrana hızlı bir biçimde ve düzenli bir şekilde dökme işlemine yarıyor.
//Socket -- Process -- Thread mantığını bildiğim kadarıyla projeyi tasarladım.
//-----------------------------------------------------------------------------------------------------------------


import java.io.IOException;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(final Stage primaryStage) {
		try {
			final AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("AutomaticCodeSender.fxml"));
			final Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setTitle("Automatic Code Sender");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(final String[] args) throws UnknownHostException, IOException {
		launch(args);
	}
}
