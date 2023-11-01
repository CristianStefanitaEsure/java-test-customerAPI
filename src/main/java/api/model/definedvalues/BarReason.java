package api.model.definedvalues;

import lombok.Getter;

import java.util.List;

public enum BarReason {

    USE_OF_VEH(BarCategory.POL),
    IMP_VEH(BarCategory.POL),
    DRIV_UNINS(BarCategory.POL),
    DISCR(BarCategory.POL),
    OTHER(BarCategory.POL);

    @Getter
    private final BarCategory category;

    BarReason(BarCategory category) {
        this.category = category;
    }

    public List<BarReason> returnAll() {
        return List.of(BarReason.values());
    }
}
