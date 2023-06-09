package com.example.myroute.yen_algorithm;

import java.util.List;
import java.util.Vector;

public class Path implements BaseElementWithWeight
{
    List<BaseVertex> _vertex_list = new Vector<BaseVertex>();
    double _weight = -1;

    public Path(){};

    public Path(List<BaseVertex> _vertex_list, double _weight)
    {
        this._vertex_list = _vertex_list;
        this._weight = _weight;
    }

    public double get_weight()
    {
        return _weight;
    }

    public void set_weight(double weight)
    {
        _weight = weight;
    }

    public List<BaseVertex> get_vertices()
    {
        return _vertex_list;
    }


    @Override
    public boolean equals(Object right)
    {
        if(right instanceof Path)
        {
            Path r_path = (Path) right;
            return _vertex_list.equals(r_path._vertex_list);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return _vertex_list.hashCode();
    }

    public String toString()
    {
        return _vertex_list.toString()+":"+_weight;
    }
}


