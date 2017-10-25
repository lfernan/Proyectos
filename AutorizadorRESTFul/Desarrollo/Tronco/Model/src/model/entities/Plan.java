package model.entities;

public class Plan {
    private Integer plan;
    private Float cuota1, resto;

    public Plan() {
    }


    public Plan(Integer plan, Float cuota1, Float resto) {
        super();
        this.plan = plan;
        this.cuota1 = cuota1;
        this.resto = resto;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }

    public Integer getPlan() {
        return plan;
    }

    public void setCuota1(Float cuota1) {
        this.cuota1 = cuota1;
    }

    public Float getCuota1() {
        return cuota1;
    }

    public void setResto(Float resto) {
        this.resto = resto;
    }

    public Float getResto() {
        return resto;
    }
}
