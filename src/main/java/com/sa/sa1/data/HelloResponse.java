package com.sa.sa1.data;

import com.sa.sa1.beans.HelloBean;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
public class HelloResponse {
    @NonNull
    private String data;
    @Autowired
    private HelloBean helloBean;

    public void setData(String name){
        data = helloBean.getTxt() + name;
    }
}
