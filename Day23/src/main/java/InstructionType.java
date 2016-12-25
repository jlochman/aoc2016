
public enum InstructionType {
    cpy("jnz"), 
    inc("dec"), 
    dec("inc"), 
    jnz("cpy"), 
    tgl("inc");
    
    private String toggledInstruction;
    
    private InstructionType(String toggledInstruction) {
	this.toggledInstruction = toggledInstruction;
    }
    
    public InstructionType toggle() {
	return InstructionType.valueOf(toggledInstruction);
    }
}
