
public class Instruction {

    InstructionType type;
    String par1;
    String par2;
    boolean toggled = false;

    public Instruction(InstructionType type, String par1, String par2) {
	this.type = type;
	this.par1 = par1;
	this.par2 = par2;
    }

    public InstructionType getType() {
	return toggled ? type.toggle() : type;
    }

    public void setType(InstructionType type) {
	this.type = type;
    }

    public String getPar1() {
	return par1;
    }

    public void setPar1(String par1) {
	this.par1 = par1;
    }

    public String getPar2() {
	return par2;
    }

    public void setPar2(String par2) {
	this.par2 = par2;
    }

    public boolean isToggled() {
	return toggled;
    }

    public void setToggled(boolean toggled) {
	this.toggled = toggled;
    }

    public String asString() {
	return type.name() + " " + par1 + " " + par2;
    }

}
