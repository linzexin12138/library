package com.wteam.modules.library.schedule;

import com.wteam.modules.library.service.UserExtraService;
import com.wteam.modules.library.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Charles
 * @since 2020/9/24 16:43
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class LibrarySchedule {

//    private String url = "jdbc:mysql://localhost:3306/library?rewriteBatchedStatements=true";
//    private String user = "root";
//    private String password = "root";

    private final UserOrderService userOrderService;
    private final UserExtraService userExtraService;


    /**
     * 每天1点删除user_order表里date字段的日期为昨天的数据
     * 和重置user_extra表里的signInFlag字段为0
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void cleanUserOrder() {

        userOrderService.deleteHistory();
        userExtraService.resetSignInFlag();

        Thread current = Thread.currentThread();
        System.out.println("定时任务1:" + current.getId());
        log.info("OrderRecordSchedule.executeFileDownLoadTask 定时任务1:" + current.getId() + ",name:" + current.getName());
    }


//
//
//
//    public void insertOrderRecord(){
//        Connection conn = null;
//        PreparedStatement pstm =null;
//        ResultSet rt = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection(url, user, password);
//            String sql = "INSERT INTO order_record(id,date,order_time_id,seat_id) VALUES(?,?,?,?)";
//            pstm = conn.prepareStatement(sql);
//            Long startTime = System.currentTimeMillis();
//            Random rand = new Random();
//            int a,b,c,d;
//            for (int i = 1; i <= 10; i++) {
//                pstm.setInt(1, i);
//                pstm.setInt(2, i);
//                a = rand.nextInt(10);
//                b = rand.nextInt(10);
//                c = rand.nextInt(10);
//                d = rand.nextInt(10);
//                pstm.setString(3, "188"+a+"88"+b+c+"66"+d);
//                pstm.setString(4, "xxxxxxxxxx_"+"188"+a+"88"+b+c+"66"+d);
//                pstm.addBatch();
//            }
//            pstm.executeBatch();
//            Long endTime = System.currentTimeMillis();
//            System.out.println("OK,用时：" + (endTime - startTime));
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }finally{
//            if(pstm!=null){
//                try {
//                    pstm.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    throw new RuntimeException(e);
//                }
//            }
//            if(conn!=null){
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
}
