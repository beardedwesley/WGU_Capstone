package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Booking;
import model.Main;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CalMonthViewController implements Initializable {
    @FXML
    private TableView<Booking> day00Tbl, day01Tbl, day02Tbl, day03Tbl, day04Tbl, day05Tbl, day06Tbl, day10Tbl,
            day11Tbl, day12Tbl, day13Tbl, day14Tbl, day15Tbl, day16Tbl, day20Tbl, day21Tbl, day22Tbl, day23Tbl,
            day24Tbl, day25Tbl, day26Tbl, day30Tbl, day31Tbl, day32Tbl, day33Tbl, day34Tbl, day35Tbl, day36Tbl,
            day40Tbl, day41Tbl, day42Tbl, day43Tbl, day44Tbl, day45Tbl, day46Tbl;

    @FXML
    private TableColumn<Booking, String> day00Col, day01Col, day02Col, day03Col, day04Col, day05Col, day06Col,
            day10Col, day11Col, day12Col, day13Col, day14Col, day15Col, day16Col, day20Col, day21Col, day22Col,
            day23Col, day24Col, day25Col, day26Col, day30Col, day31Col, day32Col, day33Col, day34Col, day35Col,
            day36Col, day40Col, day41Col, day42Col, day43Col, day44Col, day45Col, day46Col;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        day00Tbl.setItems(Main.getDayBookings(LocalDate.now()));
        day00Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day00Col.setText(((Integer) LocalDate.now().getDayOfMonth()).toString());
        day00Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day01Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(1)));
        day01Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day01Col.setText(((Integer) LocalDate.now().plusDays(1).getDayOfMonth()).toString());
        day01Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day02Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(2)));
        day02Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day02Col.setText(((Integer) LocalDate.now().plusDays(2).getDayOfMonth()).toString());
        day02Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day03Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(3)));
        day03Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day03Col.setText(((Integer) LocalDate.now().plusDays(3).getDayOfMonth()).toString());
        day03Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day04Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(4)));
        day04Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day04Col.setText(((Integer) LocalDate.now().plusDays(4).getDayOfMonth()).toString());
        day04Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day05Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(5)));
        day05Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day05Col.setText(((Integer) LocalDate.now().plusDays(5).getDayOfMonth()).toString());
        day05Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day06Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(6)));
        day06Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day06Col.setText(((Integer) LocalDate.now().plusDays(6).getDayOfMonth()).toString());
        day06Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day10Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(7)));
        day10Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day10Col.setText(((Integer) LocalDate.now().plusDays(7).getDayOfMonth()).toString());
        day10Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day11Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(8)));
        day11Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day11Col.setText(((Integer) LocalDate.now().plusDays(8).getDayOfMonth()).toString());
        day11Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day12Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(9)));
        day12Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day12Col.setText(((Integer) LocalDate.now().plusDays(9).getDayOfMonth()).toString());
        day12Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day13Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(10)));
        day13Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day13Col.setText(((Integer) LocalDate.now().plusDays(10).getDayOfMonth()).toString());
        day13Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day14Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(11)));
        day14Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day14Col.setText(((Integer) LocalDate.now().plusDays(11).getDayOfMonth()).toString());
        day14Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day15Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(12)));
        day15Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day15Col.setText(((Integer) LocalDate.now().plusDays(12).getDayOfMonth()).toString());
        day15Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day16Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(13)));
        day16Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day16Col.setText(((Integer) LocalDate.now().plusDays(13).getDayOfMonth()).toString());
        day16Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day20Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(14)));
        day20Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day20Col.setText(((Integer) LocalDate.now().plusDays(14).getDayOfMonth()).toString());
        day20Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day21Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(15)));
        day21Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day21Col.setText(((Integer) LocalDate.now().plusDays(15).getDayOfMonth()).toString());
        day21Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day22Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(16)));
        day22Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day22Col.setText(((Integer) LocalDate.now().plusDays(16).getDayOfMonth()).toString());
        day22Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day23Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(17)));
        day23Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day23Col.setText(((Integer) LocalDate.now().plusDays(17).getDayOfMonth()).toString());
        day23Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day24Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(18)));
        day24Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day24Col.setText(((Integer) LocalDate.now().plusDays(18).getDayOfMonth()).toString());
        day24Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day25Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(19)));
        day25Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day25Col.setText(((Integer) LocalDate.now().plusDays(19).getDayOfMonth()).toString());
        day25Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day26Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(20)));
        day26Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day26Col.setText(((Integer) LocalDate.now().plusDays(20).getDayOfMonth()).toString());
        day26Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day30Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(21)));
        day30Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day30Col.setText(((Integer) LocalDate.now().plusDays(21).getDayOfMonth()).toString());
        day30Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day31Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(22)));
        day31Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day31Col.setText(((Integer) LocalDate.now().plusDays(22).getDayOfMonth()).toString());
        day31Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day32Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(23)));
        day32Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day32Col.setText(((Integer) LocalDate.now().plusDays(23).getDayOfMonth()).toString());
        day32Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day33Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(24)));
        day33Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day33Col.setText(((Integer) LocalDate.now().plusDays(24).getDayOfMonth()).toString());
        day33Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day34Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(25)));
        day34Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day34Col.setText(((Integer) LocalDate.now().plusDays(25).getDayOfMonth()).toString());
        day34Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day35Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(26)));
        day35Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day35Col.setText(((Integer) LocalDate.now().plusDays(26).getDayOfMonth()).toString());
        day35Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day36Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(27)));
        day36Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day36Col.setText(((Integer) LocalDate.now().plusDays(27).getDayOfMonth()).toString());
        day36Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day40Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(28)));
        day40Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day40Col.setText(((Integer) LocalDate.now().plusDays(28).getDayOfMonth()).toString());
        day40Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day41Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(29)));
        day41Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day41Col.setText(((Integer) LocalDate.now().plusDays(29).getDayOfMonth()).toString());
        day41Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day42Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(30)));
        day42Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day42Col.setText(((Integer) LocalDate.now().plusDays(30).getDayOfMonth()).toString());
        day42Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day43Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(31)));
        day43Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day43Col.setText(((Integer) LocalDate.now().plusDays(31).getDayOfMonth()).toString());
        day43Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day44Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(32)));
        day44Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day44Col.setText(((Integer) LocalDate.now().plusDays(32).getDayOfMonth()).toString());
        day44Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day45Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(33)));
        day45Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day45Col.setText(((Integer) LocalDate.now().plusDays(33).getDayOfMonth()).toString());
        day45Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));

        day46Tbl.setItems(Main.getDayBookings(LocalDate.now().plusDays(34)));
        day46Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day46Col.setText(((Integer) LocalDate.now().plusDays(34).getDayOfMonth()).toString());
        day46Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                Main.openHome();
            }
        }));
    }
}
