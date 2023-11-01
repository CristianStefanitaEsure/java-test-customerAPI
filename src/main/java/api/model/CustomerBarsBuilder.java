package api.model;

import api.model.customernodes.CustomerBar;
import api.model.definedvalues.BarReason;
import api.model.definedvalues.BarTypes;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class CustomerBarsBuilder {

    private final CustomerBar customerBar;
    private final List<CustomerBar> barsList = new ArrayList<>();

    public CustomerBarsBuilder() {
        this.customerBar = new CustomerBar();
    }

    public CustomerBarsBuilder(CustomerBar bar) {
        this.customerBar = bar;
    }

    public CustomerBarsBuilder createMinimalBar() {
        customerBar.setBarType(BarTypes.ACC_BAR.toString());
        return this;
    }

    public CustomerBarsBuilder createUWFullBar() {
        customerBar.setBarType(BarTypes.UW_BAR.toString());
        customerBar.setReason(BarReason.OTHER.toString());
        customerBar.setCategory(BarReason.OTHER.getCategory().toString());
        customerBar.setPolicyNumber("P" + new Faker().number().digits(10));
        customerBar.setDescription("This is a description");

        return this;
    }

    //for non UW Bars category and reason is not required
    public CustomerBarsBuilder createNonUWBarsWithValue(String barType) {
        customerBar.setBarType(barType);
        customerBar.setPolicyNumber("P" + new Faker().number().digits(10));
        customerBar.setDescription("This is a description");

        return this;
    }

    public CustomerBarsBuilder createVAL_INVAndFRD_BARWithValue(String barType) {
        customerBar.setBarType(barType);
        customerBar.setPolicyNumber("P" + new Faker().number().digits(10));
        customerBar.setDescription("This is a description");
        customerBar.setReason("ACC_TAK");
        customerBar.setCategory("ORG");
        return this;
    }

    public CustomerBarsBuilder createUW_INVithValue(String barType) {
        customerBar.setBarType(barType);
        customerBar.setPolicyNumber("P" + new Faker().number().digits(10));
        customerBar.setDescription("This is a description");
        customerBar.setReason("CUST_BEH");
        customerBar.setCategory("CUST_BEH");
        return this;
    }

    public CustomerBarsBuilder createNullMandatoryFieldBar() {
        customerBar.setPolicyNumber("P" + new Faker().number().digits(10));

        return this;
    }

    public List<CustomerBar> build() {

        barsList.add(customerBar);
        return barsList;
    }

}
