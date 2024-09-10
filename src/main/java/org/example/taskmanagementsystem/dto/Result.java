package org.example.taskmanagementsystem.dto;

import lombok.*;

/**
 * This class defines the schema of the response. It is used to encapsulate data prepared by
 * the server side, this object will be serialized to JSON before sent back to the client end.
 *     private boolean flag; // Two values: true means success, false means not success
 *     private Integer code; // Status code. e.g., 200
 *     private String message; // Response message
 *     private Object data; // The response payload
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private boolean flag;

    private Integer code;

    private String message;

    private Object data;

}
