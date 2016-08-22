package org.datacharts.ui.client;

import org.datacharts.ui.shared.ConfigurationOptions;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartSubtitle;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class DataCharts implements EntryPoint {
    
    Chart chart = null;
    
    ChartsServiceAsync chartsService = GWT.create(ChartsService.class);

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    @Override
    public void onModuleLoad() {
        
       /* HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.setSpacing(1);
        hPanel.add(createChart());
        
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setSpacing(1);
        vPanel.add(createChart());
        vPanel.add(createChart());
        hPanel.add(vPanel);
        RootPanel.get("rootPanel").add(hPanel);*/
        
        Grid grid = new Grid(1, 3);
        grid.setWidget(0, 0, createDBConnectionPanel());
        grid.setWidget(0, 1, createSelectSourcePannel());
        grid.setWidget(0, 2, getChart() == null ? createChart() : getChart());
        RootPanel.get("rootPanel").add(grid);
    }

    private Widget createSelectSourcePannel() {
        
        FlexTable layout = new FlexTable();
        layout.setCellSpacing(6);
        FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();
        
        layout.setHTML(0, 0, "Select Data Source");
        cellFormatter.setColSpan(0, 0, 2);
        cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
        
        ListBox list = new ListBox();
        list.setVisibleItemCount(1);
        list.addItem("Teiid");
        list.addItem("Mysql");
        list.addItem("H2");
        
        Button applyButton = new Button("Apply", new ClickHandler() {
            public void onClick(ClickEvent sender) {
                chartsService.csvFromClasspath(list.getSelectedItemText(), new AsyncCallback<ConfigurationOptions>(){

                    @Override
                    public void onFailure(Throwable caught) {
                        
                    }

                    @Override
                    public void onSuccess(ConfigurationOptions result) {
                        // TODO Auto-generated method stub
                        
                    }});
            }
          });
        
        layout.setWidget(1, 0, list);
        layout.setWidget(2, 0, applyButton);
        
        DecoratorPanel decPanel = new DecoratorPanel();
        decPanel.setWidget(layout);
        return decPanel;
    }

    private Widget createDBConnectionPanel() {
        
        FlexTable layout = new FlexTable();
        layout.setCellSpacing(6);
        FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();
        
        layout.setHTML(0, 0, "Import Data");
        cellFormatter.setColSpan(0, 0, 2);
        cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
        
        ListBox list = new ListBox();
        list.setVisibleItemCount(1);
        list.addItem("Teiid");
        list.addItem("Mysql");
        list.addItem("H2");
        
        Button addConnButton = new Button("Add", new ClickHandler() {
                  public void onClick(ClickEvent sender) {
                    System.out.println("TODO-- add connection wizard");
                  }
                });
        
        TextArea textArea = new TextArea();
        textArea.setHeight("50px");
        textArea.setWidth("300px");

        Button applyButton = new Button("Apply", new ClickHandler() {
            public void onClick(ClickEvent sender) {
              System.out.println("TODO-- apply to query database wizard");
            }
          });
        
        layout.setHTML(1, 0, "Select Connection:");
        layout.setWidget(1, 1, list);
        layout.setWidget(1, 2, addConnButton);
        layout.setHTML(2, 0, "SQL Area:");
        layout.setWidget(2, 1, textArea);
        layout.setWidget(3, 0, applyButton);
        
        DecoratorPanel decPanel = new DecoratorPanel();
        decPanel.setWidget(layout);
        return decPanel;
    }

    private Widget createChart() {
        final Chart chart = new Chart()
            .setType(Series.Type.LINE)
            .setMarginRight(130)
            .setMarginBottom(25)
            .setChartTitle(new ChartTitle()
                .setText("Monthly Average Temperature")
                .setX(-20))
            .setChartSubtitle(new ChartSubtitle()  
                .setText("Source: WorldClimate.com")  
                .setX(-20)  
            )  
            .setLegend(new Legend()  
                .setLayout(Legend.Layout.VERTICAL)  
                .setAlign(Legend.Align.RIGHT)  
                .setVerticalAlign(Legend.VerticalAlign.TOP)  
                .setX(-10)  
                .setY(100)  
                .setBorderWidth(0)  
            )  
            .setToolTip(new ToolTip()  
                .setFormatter(new ToolTipFormatter() {  
                    public String format(ToolTipData toolTipData) {  
                        return "<b>" + toolTipData.getSeriesName() + "</b><br/>" +  
                            toolTipData.getXAsString() + ": " + toolTipData.getYAsDouble() + "°C";  
                    }  
                })  
            ); 
        
        chart.getXAxis().setCategories(  
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",  
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"  
        );  

        chart.getYAxis().setAxisTitleText("Temperature °C");  
    
        chart.addSeries(chart.createSeries()  
            .setName("Tokyo")  
            .setPoints(new Number[]{  
                7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6  
            })  
        );  
        chart.addSeries(chart.createSeries()  
            .setName("New York")  
            .setPoints(new Number[]{  
                -0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5  
            })  
        );  
        chart.addSeries(chart.createSeries()  
            .setName("Berlin")  
            .setPoints(new Number[]{  
                -0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0  
            })  
        );  
        chart.addSeries(chart.createSeries()  
            .setName("London")  
            .setPoints(new Number[]{  
                3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8  
            })  
        );  
    
        return chart;
    }

}
