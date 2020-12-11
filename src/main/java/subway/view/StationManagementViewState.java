package subway.view;

import subway.SubwayLineMap;
import subway.controller.StationController;
import subway.domain.Station;
import subway.view.component.CommonViewComponent;
import subway.view.component.StationManagementViewComponent;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StationManagementViewState extends ViewState{
    private static final String BTN_ADD_STATION = "1";
    private static final String BTN_DELETE_STATION = "2";
    private static final String BTN_READ_STATION = "3";
    private static final String BTN_BACK = "B";

    public static StationManagementViewState stationManagementViewState;
    public StationController stationController;

    private StationManagementViewState(){
        stationController = StationController.getStationController();
        featureSet.add(BTN_ADD_STATION);
        featureSet.add(BTN_DELETE_STATION);
        featureSet.add(BTN_READ_STATION);
        featureSet.add(BTN_BACK);
    }

    public static synchronized StationManagementViewState getStationManagementViewState(){
        if(!Optional.ofNullable(stationManagementViewState).isPresent()){
            stationManagementViewState = new StationManagementViewState();
        }
        return stationManagementViewState;
    }

    @Override
    protected void printMenuWithInputRequirementMsg(){
        StringBuilder viewStringBuilder = new StringBuilder();
        viewStringBuilder.append(StationManagementViewComponent.getMenuComponent());
        viewStringBuilder.append(CommonViewComponent.getWhiteLineComponent());
        viewStringBuilder.append(CommonViewComponent.getWhiteLineComponent());
        viewStringBuilder.append(CommonViewComponent.getSelectFeatureViewComponent());
        System.out.println(viewStringBuilder.toString());
    }

    @Override
    protected void runFeatureAtApplication(String feature, SubwayLineMap application, Scanner scanner) throws Exception {
        checkAndAddStation(feature, application, scanner);
        checkAndRemoveStation(feature, application, scanner);
        checkAndPrintStations(feature, application);
        checkAndSwitchViewToMain(feature, application);
    }

    private void checkAndAddStation(String feature, SubwayLineMap application, Scanner scanner) throws Exception {
        if(feature.equals(BTN_ADD_STATION)){
            printStationRegisterLog();
            String stationName = getStationName(scanner);
            printWhiteSpace();
            addStation(stationName);
            printStationRegisterFinishLog();
            switchViewToStationManagement(application);
        }
    }

    private void checkAndRemoveStation(String feature, SubwayLineMap application, Scanner scanner) throws Exception {
        if(feature.equals(BTN_DELETE_STATION)){
            printStationRemoveLog();
            String stationName = getStationName(scanner);
            printWhiteSpace();
            removeStation(stationName);
            printStationRemoveFinishLog();
            switchViewToStationManagement(application);
        }
    }

    private void checkAndPrintStations(String feature, SubwayLineMap application){
        if(feature.equals(BTN_READ_STATION)){
            List<Station> stationList = readStations();
            printStationList(stationList);
            switchViewToStationManagement(application);
        }
    }

    private void checkAndSwitchViewToMain(String feature, SubwayLineMap application){
        if(feature.equals(BTN_BACK)){
            switchViewToStationManagement(application);
        }
    }

    private String getStationName(Scanner scanner){
        return scanner.nextLine();
    }

    private void printStationRegisterLog(){
        StringBuilder viewStringBuilder = new StringBuilder();
        viewStringBuilder.append(StationManagementViewComponent.getRegisterStationComponent());
        System.out.println(viewStringBuilder.toString());
    }

    private void printStationRegisterFinishLog(){
        StringBuilder viewStringBuilder = new StringBuilder();
        viewStringBuilder.append(StationManagementViewComponent.getRegisterStationFinishComponent());
        viewStringBuilder.append(CommonViewComponent.getWhiteLineComponent());
        System.out.println(viewStringBuilder.toString());
    }

    private void printStationRemoveLog(){
        StringBuilder viewStringBuilder = new StringBuilder();
        viewStringBuilder.append(StationManagementViewComponent.getRemoveStationComponent());
        System.out.println(viewStringBuilder.toString());
    }

    private void printStationRemoveFinishLog(){
        StringBuilder viewStringBuilder = new StringBuilder();
        viewStringBuilder.append(StationManagementViewComponent.getRemoveStationFinishComponent());
        viewStringBuilder.append(CommonViewComponent.getWhiteLineComponent());
        System.out.println(viewStringBuilder.toString());
    }

    private void addStation(String name) throws Exception {
        stationController.addStation(name);
    }

    private void removeStation(String name) throws Exception {
        stationController.removeStation(name);
    }

    private List<Station> readStations() {
        return stationController.getStations();
    }

    private void printStationList(List<Station> stationList) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Station station : stationList){
            stringBuilder.append(StationManagementViewComponent.getFinishPrefixComponent());
            stringBuilder.append(station.getName());
            stringBuilder.append(CommonViewComponent.getWhiteLineComponent());
        }
        System.out.println(stringBuilder.toString());
    }

    private void switchViewToStationManagement(SubwayLineMap application){
        application.setViewState(MainViewState.getMainView());
    }
}
