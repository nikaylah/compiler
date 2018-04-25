package syntaxtree;

// nikaylahwoody
public class SubProgramNode extends ProgramNode {

    public SubProgramNode(String aName) {
        super(aName);
    }

  

    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "SubProgram: " + this.getName() + "\n";
        answer += this.getVariables().indentedToString(level + 1);
        answer += this.getFunctions().indentedToString(level + 1);
        answer += this.getMain().indentedToString(level + 1);
        return answer;
    }



	private String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}

