package api.model;

import api.model.customernodes.CustomerMarketingPermission;
import api.model.customernodes.LegitimateInterestMarketingObject;
import utils.StringUtils;
import utils.constants.LegitimateChannels;
import utils.constants.LegitimateSource;

import java.util.ArrayList;
import java.util.List;

public class LegitimateInterestBuilder {

    private ArrayList<LegitimateInterestMarketingObject> legitimatePayload;

    public LegitimateInterestBuilder() {
        this.legitimatePayload = new ArrayList<>();
    }

    public LegitimateInterestBuilder create(Boolean permissions) {
        legitimatePayload = createNewPutObject(permissions);

        return this;
    }

    public LegitimateInterestBuilder create(
            ArrayList<LegitimateInterestMarketingObject> permission) {
        legitimatePayload = permission;

        return this;
    }

    public LegitimateInterestBuilder withChannelValueSetTo(String value) {
        legitimatePayload
                .get(0)
                .getCustomerMarketingPermissions()
                .get(0)
                .setChannel(value);

        return this;
    }

    public LegitimateInterestBuilder setEmail(Boolean value) {
        modifyOrCreateChannel(LegitimateChannels.EMAIL.getChannel(), value);

        return this;
    }

    public LegitimateInterestBuilder setSms(Boolean value) {
        modifyOrCreateChannel(LegitimateChannels.SMS.getChannel(), value);

        return this;
    }

    public LegitimateInterestBuilder setPost(Boolean value) {
        modifyOrCreateChannel(LegitimateChannels.POST.getChannel(), value);

        return this;
    }

    public LegitimateInterestBuilder setPhone(Boolean value) {
        modifyOrCreateChannel(LegitimateChannels.PHONE.getChannel(), value);

        return this;
    }

    public LegitimateInterestBuilder setChannelsWithTheSameMarketValue(Boolean expectedValued) {
        legitimatePayload.get(0)
                .getCustomerMarketingPermissions()
                .stream().forEach((permission) -> permission.setCanMarket(expectedValued));

        return this;
    }

    public LegitimateInterestBuilder setChannelsWithTheSameSourceValue(String expectedValued) {
        legitimatePayload.get(0)
                .getCustomerMarketingPermissions()
                .stream().forEach((permission) -> permission.setSource(expectedValued));

        return this;
    }

    private ArrayList<LegitimateInterestMarketingObject> createNewPutObject(
            Boolean bulkPermissions) {
        ArrayList<LegitimateInterestMarketingObject> list = new ArrayList<>();
        list.add(new LegitimateInterestMarketingObject().setBrand("ES")
                .setCustomerMarketingPermissions(createNewMarketingPermissionsList(bulkPermissions)));

        return list;
    }

    private void modifyOrCreateChannel(String channelName, Boolean value) {
        if (checkChannelExists(channelName)) {
            setChannelValue(channelName, value);
        } else {
            createNewChannel(channelName, value);
        }
    }

    private void setChannelValue(String channelName, Boolean value) {
        for (CustomerMarketingPermission permission :
                legitimatePayload.get(0)
                        .getCustomerMarketingPermissions()
        ) {
            if (permission.getChannel().equals(channelName)) {
                permission.setCanMarket(value);
            }
        }
    }

    private CustomerMarketingPermission createNewChannel(String channel, Boolean value) {
        return new CustomerMarketingPermission().setChannel(channel).setCanMarket(value)
                .setSource(LegitimateSource.QUOTE_JOURNEY.name());
    }

    private List<CustomerMarketingPermission> createNewMarketingPermissionsList(
            Boolean bulkMarketPermission) {
        List<CustomerMarketingPermission> permissions = new ArrayList<>();
        permissions.add(
                createNewPermission(LegitimateChannels.EMAIL.getChannel(), bulkMarketPermission,
                        LegitimateSource.QUOTE_JOURNEY.name()));
        permissions.add(
                createNewPermission(LegitimateChannels.POST.getChannel(), bulkMarketPermission,
                        LegitimateSource.QUOTE_JOURNEY.name()));
        permissions.add(
                createNewPermission(LegitimateChannels.PHONE.getChannel(), bulkMarketPermission,
                        LegitimateSource.QUOTE_JOURNEY.name()));
        permissions.add(
                createNewPermission(LegitimateChannels.SMS.getChannel(), bulkMarketPermission,
                        LegitimateSource.QUOTE_JOURNEY.name()));

        return permissions;
    }

    private CustomerMarketingPermission createNewPermission(String channel, Boolean canMarket,
                                                            String source) {
        return new CustomerMarketingPermission().setChannel(channel).setCanMarket(canMarket)
                .setSource(source).setUpdated(StringUtils.getEnhancedDate());
    }

    private Boolean checkPermissionsExist() {
        return legitimatePayload.get(0)
                .getCustomerMarketingPermissions()
                .isEmpty();
    }


    private Boolean checkChannelExists(String channel) {
        return legitimatePayload.get(0)
                .getCustomerMarketingPermissions()
                .stream().anyMatch((permission) -> permission.getChannel().equals(channel));
    }

    public List<LegitimateInterestMarketingObject> build() {
        return legitimatePayload;
    }

}
