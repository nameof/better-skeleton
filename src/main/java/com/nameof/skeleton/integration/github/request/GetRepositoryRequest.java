package com.nameof.skeleton.integration.github.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRepositoryRequest {
    private String name;
}
