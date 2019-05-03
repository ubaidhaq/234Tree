package trees;

import java.io.*;                 

class Node
   {
   private int ORDER = 4;
   private int numItems;
   private Node parent;
   private Node childArray[] = new Node[ORDER];
   public int itemArray[] = new int[ORDER-1];

   public void connectChild(int childNum, Node child)
      {
      childArray[childNum] = child;
      if(child != null)
         child.parent = this;
      }
   public Node disconnectChild(int childNum)
      {
      Node temp = childArray[childNum];
      childArray[childNum] = null;
      return temp;
      }
// -------------------------------------------------------------
   public Node getChild(int childNum)
      { return childArray[childNum]; }
// -------------------------------------------------------------
   public Node getParent()
      { return parent; }
// -------------------------------------------------------------
   public boolean isLeaf()
      { return (childArray[0]==null) ? true : false; }
// -------------------------------------------------------------
   public int getNumItems()
     { return numItems; }
// -------------------------------------------------------------
   public int getItem(int index)   
      { return itemArray[index]; }
// -------------------------------------------------------------
   public boolean isFull()
      { return (numItems==ORDER-1) ? true : false; }
// -------------------------------------------------------------
   public int findItem(int key) //searches Node for item. if found -> index. else -1 
      {                                    
      for(int j=0; j<ORDER-1; j++)         
         {                       
         if(itemArray[j] == 0)  //0 is considered empty. Null. no value  
            break;
         else if(itemArray[j] == key)
            return j;
         }
      return -1;
      }

   public int insertItem(int val) 
      {
      // assumes node is not full
      numItems++;                         
           
      for(int j=numItems-1; j>=0; j--)        // start at last
         {                                
         if(itemArray[j] == 0)          // if item null,
            continue;                      // go left one cell
         else                              
            {
            if(val < itemArray[j])            // if it's bigger
               itemArray[j+1] = itemArray[j]; // shift it right
            else
               {
               itemArray[j+1] = val;   // insert new item
               return j+1;                 // return index to
               }                          
            }
         }                     
      itemArray[0] = val; // if it is smallest, then insert at index 0
      return 0;
      }  

   public int removeItem()   // remove largest item
      {
        int temp = itemArray[numItems-1];  // save item
        itemArray[numItems-1] = 0;           // disconnect it
        numItems--;                             // one less item
        return temp;                            // return item
      }

   public void displayNode()          
      {
      for(int j=0; j<numItems; j++)
              System.out.print(itemArray[j] + " ");  
      }
}

   class Tree234
   {
   private Node root = new Node();            // make root node

   public int find(int key)
      {
      Node curNode = root;
      int childNumber;
      while(true)
         {
            childNumber = curNode.findItem(key);
             
            if(childNumber != -1)
               return childNumber;               // found it
            else if( curNode.isLeaf() )
               return -1;                        // can't find it
            else                                 
               curNode = getNextChild(curNode, key); // search deeper
         }  
      }
   
   public void insert(int dValue)
      {
      Node curNode = root;

      while(true)
         {
         if( curNode.isFull() )               // if node full,
            {
            split(curNode);                   
            curNode = curNode.getParent();    // back up
                                              // search once
            curNode = getNextChild(curNode, dValue);
            }  // end if(node is full)

         else if( curNode.isLeaf() )          // if node is leaf,
            break;                            // go insert
         // node is not full, not a leaf; so go to lower level
         else
            curNode = getNextChild(curNode, dValue);
         }  // end while

      curNode.insertItem(dValue);       // insert sirf leaf par hogi
      }  
// -------------------------------------------------------------
   public void split(Node thisNode)     // split the node
      {
       
//          System.out.println("split called: " + thisNode.getNumItems());
               // num of items 3 hain.
               
               
      // assumes node is full
      int itemB, itemC;
      Node parent, child2, child3;
      int itemIndex;

      itemC = thisNode.removeItem();    // remove items from
      itemB = thisNode.removeItem();    // this node
      child2 = thisNode.disconnectChild(2); // remove children
      child3 = thisNode.disconnectChild(3); // from this node

      Node newRight = new Node();       // make new node

      if(thisNode==root)                // if this is the root,
         {
         root = new Node();                // make new root
         parent = root;                    // root is our parent
         root.connectChild(0, thisNode);   // connect to parent
         }
      else                              // this node not the root
         parent = thisNode.getParent();    // get parent

      // deal with parent
      itemIndex = parent.insertItem(itemB); // item B to parent
      int n = parent.getNumItems();         // total items?

      for(int j=n-1; j>itemIndex; j--)          // move parent's
         {                                      // connections
         Node temp = parent.disconnectChild(j); // one child
         parent.connectChild(j+1, temp);        // to the right
         }
                                   // connect newRight to parent
      parent.connectChild(itemIndex+1, newRight);

      // deal with newRight
      newRight.insertItem(itemC);       // item C to newRight
      newRight.connectChild(0, child2); // connect to 0 and 1
      newRight.connectChild(1, child3); // on newRight
      }  // end split()

   // gets appropriate child of node during search for value
   public Node getNextChild(Node theNode, int theValue)
      {
      int j;

      int numItems = theNode.getNumItems();
        for(j=0; j<numItems; j++)          
        {                               
            if( theValue < theNode.itemArray[j])
            {
                return theNode.getChild(j);
            }  // return left child
         }                    
      return theNode.getChild(j);        // return right child
      }

   public void displayTree()
      {
      recDisplayTree(root, 0, 0);
      }

   private void recDisplayTree(Node thisNode, int level, int childNumber)
      {
        System.out.print("Level: "+level+", Child: "+childNumber + ", Values: ");
        thisNode.displayNode();              
        System.out.println("");
      
        //print all child
      
      int numItems = thisNode.getNumItems();
      
      for(int j=0; j<numItems+1; j++)
         {
         Node nextNode = thisNode.getChild(j);
         if(nextNode != null)
            recDisplayTree(nextNode, level+1, j);
         else
            return;
         }
      }  
   }  


   class Tree234App
   {
   public static void main(String[] args) throws IOException
      {
      int value;
      Tree234 theTree = new Tree234();

      theTree.insert(50);
      theTree.insert(40);
      theTree.insert(60);
      theTree.insert(30);
      theTree.insert(70);

      while(true)
         {
         putText("Enter first letter of ");
         putText("show, insert, or find: ");
         char choice = getChar();
         switch(choice)
            {
            case 's':
               theTree.displayTree();
               break;
            case 'i':
               putText("Enter value to insert: ");
               value = getInt();
               theTree.insert(value);
               break;
            case 'f':
               putText("Enter value to find: ");
               value = getInt();
               int found = theTree.find(value);
               if(found != -1)
                  System.out.println("Found "+value);
               else
                  System.out.println("Could not find "+value);
               break;
            default:
               putText("Invalid entry\n");
            }  // end switch
         }  // end while
      }  // end main()
//--------------------------------------------------------------
   public static void putText(String s)
      {
      System.out.print(s);
      System.out.flush();
      }
//--------------------------------------------------------------
   public static String getString() throws IOException
      {
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);
      String s = br.readLine();
      return s;
      }
//--------------------------------------------------------------
   public static char getChar() throws IOException
      {
      String s = getString();
      return s.charAt(0);
      }

//-------------------------------------------------------------
   public static int getInt() throws IOException
      {
      String s = getString();
      return Integer.parseInt(s);
      }
//-------------------------------------------------------------
   }  // end class Tree234App

