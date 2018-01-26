import com.taotao.util.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

public class FastDFSTest {



    @Test
    public void testUploadFile()  throws Exception{
        //1.创建一个配置文件 用来连接tracker_server

        //2.加载配置文件
        ClientGlobal.init("C:\\Users\\ThinkPad\\IdeaProjects\\taotao_parent\\taotao_manager_web\\src\\main\\resources\\resource\\fastdfs.conf");

        //3.构建trackerclient对象
        TrackerClient trackerClient = new TrackerClient();
        //4.构建trackerserver对象
        TrackerServer trackerServer = trackerClient.getConnection();

        //5.构建一个storageServer对象
        StorageServer storageServer = null;

        //6.构建一个storageClient
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);


        //7.使用storageClient 上传图片
        //第一个参数：要上传的图片的本地路径
        //第二个参数：就是图片的扩展名不带点
        //第三个参数：源数据 图片的高度 宽度 像素  作者  时间戳
        String[] strings = storageClient.upload_file("C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg", "jpg", null);

        for (String string : strings) {
            System.out.println(string);
        }
    }

    @Test
    public void testFastDfsClient() throws  Exception{
        FastDFSClient client = new FastDFSClient("C:\\Users\\ThinkPad\\IdeaProjects\\taotao_parent\\taotao_manager_web\\src\\main\\resources\\resource\\fastdfs.conf");
        String filepath = client.uploadFile("C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg", "jpg");
        System.out.println(filepath);
    }

    public static void main(String[] args) {
        float a = 130f;//130f  分
        double b = 130d;//130d 分

        float aa = a * 3;
        double bb = b * 3;
        System.out.println(aa);
        System.out.println(bb);

    }
}
