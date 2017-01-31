package com.alexfrndz.service;

import com.alexfrndz.converter.utils.XMLConverterUtils;
import com.alexfrndz.domain.OrderEntity;
import com.alexfrndz.pojo.Order;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class ScheduleComponent {

    @Resource
    private Environment env;

    @Autowired
    private OrderServiceImpl orderService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 30000)
    public void findAllFilesInDirectory() {

        String basePath = env.getRequiredProperty("app.order.path");
        String fileExtension = env.getRequiredProperty("app.order.fileExt");
        String arcExtension = env.getRequiredProperty("app.order.arcExt");

        log.info("Fetching files from directory {} @ {}", basePath, dateFormat.format(new Date()));

        List<File> allFilesWithCertainExtension = getAllFilesWithCertainExtension(new File(basePath), fileExtension);
        if (allFilesWithCertainExtension.size() > 0) {
            String theFileToProcess = allFilesWithCertainExtension.get(0).getAbsolutePath();
            log.info("Fetched File : {}", theFileToProcess);
            try {
                Order order = (Order) XMLConverterUtils.xmlToObject(orderService.getXmlMarshaller(), theFileToProcess);
                if (order != null) {
                    OrderEntity createdOrder = orderService.createOrder(order);
                    if (createdOrder != null) {
                        log.info("Order has been created successfully with id of ({})", createdOrder.getId());
                        // Archive the file
                        String archivedFileName = theFileToProcess.substring(0, theFileToProcess.lastIndexOf(".")) + "." + arcExtension;
                        FileUtils.moveFile(
                                FileUtils.getFile(theFileToProcess),
                                FileUtils.getFile(archivedFileName)
                        );
                        log.info("{} has been archived.", archivedFileName);
                    }
                }
            } catch (IOException e) {
                log.error("Unable to Unmarshall the file: {}, {}", theFileToProcess, e.getMessage());
            }
        }
    }


    public List<File> getAllFilesWithCertainExtension(File directory, String filterExt) {
        List<File> filesFromFilter = Lists.newArrayList();
        if (!directory.isDirectory()) {
            return filesFromFilter;
        }

        MyExtFilter extFilter = new MyExtFilter(filterExt);
        File[] files = directory.listFiles(extFilter);

        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return Long.compare(f1.lastModified(), f2.lastModified());
            }
        });
        return Lists.newArrayList(files);
    }


    public class MyExtFilter implements FilenameFilter {

        private String ext;

        public MyExtFilter(String ext) {
            this.ext = ext;
        }

        public boolean accept(File dir, String name) {
            return (name.endsWith(ext));
        }
    }


}