//package com.wteam.modules.library.schedule;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.sql.*;
//
///**
// * @Author: Charles
// * @Date: 2020/9/24 16:43
// */
//@Component
//@Slf4j
//public class OrderRecordSchedule {
//
//    private String url = "jdbc:mysql://localhost:3306/library?rewriteBatchedStatements=true";
//    private String user = "root";
//    private String password = "root";
//
//    /**
//     * 每天0点生成新的座位预定记录，只有userId字段是空的
//     */
//    @Scheduled(cron="0 0 0 1/1 * ? ")
//    public void createOrderRecord() {
//
//        insertOrderRecord();
//
//        Thread current = Thread.currentThread();
//        System.out.println("定时任务1:"+current.getId());
//        log.info("OrderRecordSchedule.executeFileDownLoadTask 定时任务1:"+current.getId()+ ",name:"+current.getName());
//    }
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
//}
