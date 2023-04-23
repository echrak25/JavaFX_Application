module Boutiqu {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	exports boutique;
    opens boutique to javafx.base;
    requires javafx.base;
	opens application to javafx.graphics, javafx.fxml,javafx.base;
}
