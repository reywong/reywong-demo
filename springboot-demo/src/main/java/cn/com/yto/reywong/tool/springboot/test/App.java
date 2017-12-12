/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cn.com.yto.reywong.tool.springboot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        StringBuffer html = new StringBuffer();
        String title = "ss";
        String path = "";
        html.append("" +
                "<meta charset=\"utf-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "\n" +
                "<title>" + title + "</title>" +
                "<!--jq-->\n" +
                "<script src=\"" + path + "/js/jquery-2.1.1.js\"></script>\n" +
                "<!--bootstrap-->\n" +
                "<link href=\"" + path + "/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "<script src=\"" + path + "/js/bootstrap.min.js\"></script>\n" +
                "\n" +
                "<link href=\"" + path + "/font-awesome/css/font-awesome.css\" rel=\"stylesheet\">\n" +
                "<link href=\"" + path + "/css/animate.css\" rel=\"stylesheet\">\n" +
                "<link href=\"" + path + "/css/style.css\" rel=\"stylesheet\">\n" +
                "\n" +
                "\n" +
                "<script src=\"" + path + "/js/plugins/metisMenu/jquery.metisMenu.js\"></script>\n" +
                "<script src=\"" + path + "/js/plugins/slimscroll/jquery.slimscroll.min.js\"></script>\n" +
                "<script src=\"" + path + "/js/inspinia.js\"></script>\n");
        System.out.println(html.toString());
    }

}
