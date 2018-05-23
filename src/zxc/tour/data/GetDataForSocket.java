package zxc.tour.data;

import org.json.JSONArray;
import org.json.JSONObject;
import zxc.tour.DBhandle.SaveDataToDB;
import zxc.tour.DBhandle.SelectDB;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetDataForSocket {
    //定义相关的参数,端口,存储Socket连接的集合,ServerSocket对象
    //以及线程池
    private static final int PORT = 12345;
    private List<Socket> mList = new ArrayList<Socket>();
    private ServerSocket server = null;
    private ExecutorService myExecutorService = null;

    private String id;


    public static void main(String[] args) {
        new GetDataForSocket();
    }

    public GetDataForSocket()
    {
        try
        {
            server = new ServerSocket(PORT);
            //创建线程池
            myExecutorService = Executors.newCachedThreadPool();
            System.out.println("服务端运行中...\n");
            Socket client = null;
            while(true)
            {
                client = server.accept();
                mList.add(client);
                myExecutorService.execute(new Service(client));
            }

        }catch(Exception e){e.printStackTrace();}
    }

    class Service implements Runnable
    {
        private Socket socket;
        private BufferedReader in = null;
        private String msg = "";


        public Service(Socket socket) {
            this.socket = socket;
            try
            {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                msg = in.readLine();
                System.out.println(msg);
                JSONObject jsonObject = new JSONObject(msg);
                String doWhat = jsonObject.get("doWhat").toString();
                if (doWhat.equals("getbrowse")) {
                    this.sendArticleToClient("201801");
                } else if (doWhat.equals("article")) {
                    //获取article中的主要内容
                    String article = jsonObject.get("Title").toString();
                    String content = jsonObject.get("Content").toString();
                    //存储article到数据库
                    try {
                        SaveDataToDB.getInstance().saveToArticle(article,content,id);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else if (doWhat.equals("reserve")) {
                    //获取reserve中的主要内容
                    String begin = jsonObject.get("begin").toString();
                    String stop = jsonObject.get("stop").toString();
                    try {

                        SaveDataToDB.getInstance().saveToReserve(begin, stop, id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (doWhat.equals("siteorder")) {
                    System.out.println(jsonObject);
                    try {
                        SaveDataToDB.getInstance().saveToSiteOrder(jsonObject.get("projectname").toString(), jsonObject.get("price").toString(), id);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (doWhat.equals("getMyOrder")) {

                    this.sendUserOrder("201801");

                } else {
                    msg = "用户:" + this.socket.getInetAddress() + "~登入系统";
                }

                this.sendmsg();
            }catch(IOException e){
                e.printStackTrace();
            }
        }



        @Override
        public void run() {
            try{
                while(true)
                {
                    if((msg = in.readLine()) != null)
                    {

                        JSONObject jsonObject = new JSONObject(msg);
                        String doWhat = jsonObject.get("doWhat").toString();
                        if(doWhat.equals("bye"))
                        {
                            //客户端断开网络
                            System.out.println("~~~~~~~~~~~~~");
                            mList.remove(socket);
                            in.close();
                            socket.close();
                            this.sendmsg();
                            break;
                        } else if (doWhat.equals("username")) {
                            //获取到username
                            String username = jsonObject.get("username").toString();
                            //从数据库查找到id
                            String id = SelectDB.getInstance().getUserId(username);

                            System.out.println(id);
                            setUserID(id);

                        } else if (doWhat.equals("site")) {
                            //根据用户名查找用户ID，根据用户ID查找电话号码
                           /* msg = SelectDB.getInstance().getUserId(jsonObject.get("name").toString());
                            msg = SelectDB.getInstance().getUserPhoneNumber(msg);*/

                            in.close();
                            socket.close();
                            System.out.println(msg);
                            /*System.out.println(jsonObject.get("name"));*/
                           // this.sendmsg();
                            this.sendToHandle();
                        } else if (doWhat.equals("article")) {
                            //获取article中的主要内容
                            String article = jsonObject.get("Title").toString();
                            String content = jsonObject.get("Content").toString();
                            //存储article到数据库
                            SaveDataToDB.getInstance().saveToArticle(article,content,id);
                        } else if (doWhat.equals("getbrowse")) {
                            this.sendArticleToClient(id);
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }



        //为连接上服务端的每个客户端发送信息
        public void sendmsg()
        {
            int num = mList.size();
            for(int index = 0;index < num;index++)
            {
                Socket mSocket = mList.get(index);
                PrintWriter pout = null;
                try {
                    pout = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(mSocket.getOutputStream(),"UTF-8")),true);
                    pout.println(msg);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendUserOrder(String id) {

            PrintWriter pout = null;

            String projectName = SelectDB.getInstance().getUserOrderProjectName(id);
            String price = SelectDB.getInstance().getUserOrderPrice(id);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("projectname", projectName);
            jsonObject.put("price", price);

            String result = jsonObject.toString();
            try {
                pout = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(),"UTF-8")),true);
                pout.println(result);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendArticleToClient(String id){

            PrintWriter pout = null;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("article", SelectDB.getInstance().getArticleTilte(id));
            jsonObject.put("content", SelectDB.getInstance().getArticleContent(id));
            jsonObject.put("id", id);
            String result = jsonObject.toString();

            try {
                pout = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(),"UTF-8")),true);
                pout.println(result);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void sendToHandle(){
            System.out.println("sdf");

            PrintWriter pout = null;
            /*JSONObject jsonObject = new JSONObject();
            jsonObject.put("article", SelectDB.getInstance().getArticleTilte(id));
            jsonObject.put("content", SelectDB.getInstance().getArticleContent(id));
            jsonObject.put("id", id);
            String result = jsonObject.toString();*/

            try {
                pout = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(),"UTF-8")),true);
                pout.println("zhangsfosdf");
                System.out.println("发送成功");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void setUserID(String ID) {
        this.id = ID;
    }
}