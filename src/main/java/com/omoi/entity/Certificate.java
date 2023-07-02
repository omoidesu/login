package com.omoi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author omoi
 * @date 2023/7/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
    private String id;
    private String key;
    private String salt;
}
