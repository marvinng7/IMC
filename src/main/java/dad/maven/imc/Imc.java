package dad.maven.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class Imc extends Application{

	private Label label1;
	private Label label2;
	private Label label3;
	private Label label4;
	private Label label5;
	private Label label6;
	private Label label7;

	private TextField numeroTexto1;
	private TextField numeroTexto2;
	
	private DoubleProperty n1= new SimpleDoubleProperty();
	private DoubleProperty n2= new SimpleDoubleProperty();
	private DoubleProperty resultado = new SimpleDoubleProperty();
	private DoubleProperty alturaMetros = new SimpleDoubleProperty();
	
	private BooleanProperty obeso = new SimpleBooleanProperty();
	private BooleanProperty normal = new SimpleBooleanProperty();
	private BooleanProperty sobrePeso = new SimpleBooleanProperty();
	private BooleanProperty bajoPeso = new SimpleBooleanProperty();
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
	label1= new Label("Peso:");
	label2= new Label("kg");
	label3= new Label("Altura:");
	label4= new Label("cm");
	label5= new Label("IMC: ");
	label6= new Label();
	label7= new Label();
	
	numeroTexto1 = new TextField();
	numeroTexto2 = new TextField();
	
	numeroTexto1.setMaxWidth(80);
	numeroTexto2.setMaxWidth(80);
	
	HBox box1 = new HBox(5, label1,numeroTexto1,label2);
	box1.setAlignment(Pos.CENTER);
	box1.setFillHeight(false);
	
	HBox box2 = new HBox(5, label3,numeroTexto2,label4);
	box2.setAlignment(Pos.CENTER);
	box2.setFillHeight(false);
	
	HBox box3 = new HBox();
	box3.setAlignment(Pos.CENTER);
	box3.setFillHeight(false);
	box3.getChildren().addAll(label5,label6);
	
	HBox box4 = new HBox();
	box4.setAlignment(Pos.CENTER);
	box4.setFillHeight(false);
	box4.getChildren().addAll(label7);
	
	
	VBox root = new VBox(box1, box2, box3, box4);
	root.setAlignment(Pos.CENTER);
	root.setFillWidth(false);

	Scene scene = new Scene(root, 300, 200);
	
	primaryStage.setTitle("IMC.fxml");
	primaryStage.setScene(scene);
	primaryStage.show();
	
	Bindings.bindBidirectional(numeroTexto1.textProperty(), n1, new NumberStringConverter());
	Bindings.bindBidirectional(numeroTexto2.textProperty(), n2, new NumberStringConverter());
		
	alturaMetros.bind(n2.divide(100));
	resultado.bind(n1.divide(alturaMetros.multiply(alturaMetros)));
	
	label6.textProperty().bind(Bindings.concat(resultado.asString()));

	bajoPeso.bind(resultado.lessThan(18.5f));
	normal.bind(resultado.greaterThanOrEqualTo(18.5f).and(resultado.lessThan(25)));
	sobrePeso.bind(resultado.greaterThanOrEqualTo(25f).and(resultado.lessThan(30)));
	obeso.bind(resultado.greaterThanOrEqualTo(30f));
	
	StringExpression pesoExpresion = (
									Bindings
									.concat(Bindings.when(bajoPeso).then("Bajo peso"))
									.concat(Bindings.when(normal).then("Normal"))
									.concat(Bindings.when(obeso).then("Obeso"))
									.concat(Bindings.when(sobrePeso).then("Sobrepeso")));
	
	label7.textProperty().bind((pesoExpresion));
	
	
	}
	
	
public static void main(String[] args) {
		launch(args);

	}

}
