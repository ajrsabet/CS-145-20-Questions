// A QuestionNode object is one node in a binary tree 
public class QuestionNode {
    
        public String data;
        public QuestionNode yesNode;
        public QuestionNode noNode;

        public QuestionNode(String data) {
            this.data = data;
            this.yesNode = null;
            this.noNode = null;
            }
    
            public boolean isLeaf() {
                return yesNode == null && noNode == null;
            }
        

}
