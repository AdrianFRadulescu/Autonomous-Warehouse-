package jobselection;

public class Test {

    public static String FOLDER_PATH = "/Users/adrian_radulescu1997/Desktop/Uni-Courses/RobotProgramming/Warehouse/src/inputs/";

    private static Integer worldXSize = 15;
    private static Integer worldYSize = 15;

    /*

    @SuppressWarnings("unchecked")
    public static void main(String[] args){

        ItemTable itemTable;

        try {
            //read the file containing the items and put them into a table
            itemTable = FileContentReader.getItemFileContent(FOLDER_PATH + "items.csv");

        }catch (IOException e) {
            System.out.println("IOException---");
            itemTable = null;
        }


        try{
            //read the file containing the locations of the items and set them in the table
            itemTable = FileContentReader.addLocationsFileContent(FOLDER_PATH + "locations.csv",itemTable);

        }catch (IOException e){

            System.out.println("IOException");
            itemTable = null;
        }


        Job a = new Job("10008,ah,2,aj,2,ab,3,ch,5",itemTable);

        System.out.println(a);

        WorldEntry w = worldDistances();

        Integer[] rx_coord = new Integer[1];
        Integer[] ry_coord = new Integer[1];

        rx_coord[0] = itemTable.get("ah").getX_coord();
        ry_coord[0] = itemTable.get("ah").getY_coord();

        LinkedList<Drop> drops = new LinkedList<>();
        drops.add(new Drop("4,7"));

        a.orderPicking(w,itemTable,rx_coord,ry_coord,drops);

        System.out.println(a);


    }

    private static WorldEntry worldDistances(){

        Integer[][][][] distances = new Integer[worldXSize][worldYSize][worldXSize][worldYSize];
        String[][][][] routes = new String[worldXSize][worldYSize][worldXSize][worldYSize];

        //the s before the iterator represents start and the g represnts goal

        int i = 0;

        for(int si = 0; si < worldXSize; si++){
            for(int sj = 0; sj < worldYSize; sj++){
                for(int gi = 0; gi < worldXSize; gi++){
                    for(int gj = 0; gj < worldYSize; gj++){

                        AStar as = new AStar(new routeplanning.State(si,sj),new routeplanning.State(gi,gj));
                        //routes[si][sj][gi][gj] = as.findPath();
                        //distances[si][sj][gi][gj] = routes[si][sj][gi][gj].length();
                        distances[si][sj][gi][gj] = as.manhatanDistace(new routeplanning.State(si,sj),new routeplanning.State(gi,gj));

                    }
                }
            }
        }

        return new WorldEntry(distances,routes);

    }
    /*/

    public static void main(String[] args){

        /*
        JobSelectionMain a = new JobSelectionMain();

        System.out.println(System.currentTimeMillis());

        a.run();

        System.out.println(a.selectJob());

        System.out.println(System.currentTimeMillis());

        Integer[] x = new Integer[1];

        x[0] = 5;

        a.setRx_coord(x);
        a.setRy_coord(x);
        a.resetJobQueue();

        System.out.println(a.selectJob());

        System.out.println(System.currentTimeMillis());
        */

        boolean ok = true;
        ok &= false;
        System.out.println(ok);


    }

}
