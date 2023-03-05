package com.sa.sa1.data;

import com.sa.sa1.beans.HelloBean;
import com.sa.sa1.utilities.ApplicationContextProvider;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@Data
@NoArgsConstructor
public class HelloResponse {
    @NonNull
    private String data;
    @Autowired
    private HelloBean helloBean;

    private ApplicationContextProvider applicationContextProvider;

    public void setData(String name) {
        if (helloBean == null) {
            if (applicationContextProvider != null) {
                helloBean = applicationContextProvider.getApplicationContext().getBean(HelloBean.class);
            }
            if (helloBean == null) {
                data = name;
            } else {
                data = helloBean.getTxt() + name;
            }
        } else {
            data = helloBean.getTxt() + name;
        }
    }
}
