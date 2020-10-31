package com.sorintlab.demo;

public enum DistributedObjectType {

    Map("getMap"),
    Queue("getQueue"),
    ReplicatedMap("getReplicatedMap");

    private String accessorName;

    DistributedObjectType(String accessorName) {
        this.accessorName = accessorName;
    }

    String getAccessorName() {
        return this.accessorName;
    }

}
