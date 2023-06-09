package com.example.myroute.yen_algorithm;

import com.example.myroute.data_base.Way;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;


public class Graph implements BaseGraph
{
    public final static double DISCONNECTED = Double.MAX_VALUE;

    // индекс разветвлений одной вершины
    protected Map<Integer, Set<BaseVertex>> _fanout_vertices_index =
            new HashMap<Integer, Set<BaseVertex>>();

    // индекс для разветвлений одной вершины
    protected Map<Integer, Set<BaseVertex>> _fanin_vertices_index =
            new HashMap<Integer, Set<BaseVertex>>();

    // индекс для весов ребер на графе
    protected Map<Pair<Integer, Integer>, Double> _vertex_pair_weight_index =
            new HashMap<Pair<Integer,Integer>, Double>();

    // индекс для вершин в графе
    protected Map<Integer, BaseVertex> _id_vertex_index =
            new HashMap<Integer, BaseVertex>();

    // список вершин в графе
    protected List<BaseVertex> _vertex_list = new Vector<BaseVertex>();

    // количество вершин в графе
    protected int _vertex_num = 0;

    // количество дуг на графике
    protected int _edge_num = 0;

    public Graph(final String data_file_name)
    {
        import_from_file(data_file_name);
    }

    public Graph(List<Way> stores1, int k)
    {
        import_from_stores(stores1, k);
    }


    public Graph(final Graph graph_)
    {
        _vertex_num = graph_._vertex_num;
        _edge_num = graph_._edge_num;
        _vertex_list.addAll(graph_._vertex_list);
        _id_vertex_index.putAll(graph_._id_vertex_index);
        _fanin_vertices_index.putAll(graph_._fanin_vertices_index);
        _fanout_vertices_index.putAll(graph_._fanout_vertices_index);
        _vertex_pair_weight_index.putAll(graph_._vertex_pair_weight_index);
    }

    public Graph(){};

    public void clear()
    {
        _vertex_num = 0;
        _edge_num = 0;
        _vertex_list.clear();
        _id_vertex_index.clear();
        _fanin_vertices_index.clear();
        _fanout_vertices_index.clear();
        _vertex_pair_weight_index.clear();
    }

    public void import_from_file(final String data_file_name)
    {
        try
        {
            // 1. прочитайте файл и поместите его содержимое в буфер
            FileReader input = new FileReader(data_file_name);
            BufferedReader bufRead = new BufferedReader(input);

            boolean is_first_line = true;
            String line; 	// Строка, содержащая текущую строку файла

            // 2. Прочитайте первую строку
            line = bufRead.readLine();
            while(line != null)
            {
                // 2.1 пропустить пустую строку
                if(line.trim().equals(""))
                {
                    line = bufRead.readLine();
                    continue;
                }

                // 2.2 сгенерировать узлы и ребра для графика
                if(is_first_line)
                {
                    //2.2.1 получить количество узлов в графе

                    is_first_line = false;
                    _vertex_num = Integer.parseInt(line.trim());
                    for(int i=0; i<_vertex_num; ++i)
                    {
                        BaseVertex vertex = new Vertex();
                        _vertex_list.add(vertex);
                        _id_vertex_index.put(vertex.get_id(), vertex);
                    }

                }else
                {
                    //2.2.2 найдите новое ребро и поместите его на график
                    String[] str_list = line.trim().split("\\s");
                    //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    //System.out.println(Arrays.toString(str_list));

                    int start_vertex_id = Integer.parseInt(str_list[0]);
                    int end_vertex_id = Integer.parseInt(str_list[1]);
                    double weight = Double.parseDouble(str_list[2]);
                    add_edge(start_vertex_id, end_vertex_id, weight);
                }
                //
                line = bufRead.readLine();
            }
            bufRead.close();

        }catch (IOException e)
        {
            // Если генерируется другое исключение, выведите трассировку стека
            e.printStackTrace();
        }
    }

    public void import_from_stores(List<Way> stores1, int k)
    {
        _vertex_num = k;
        for(int i=0; i<_vertex_num; ++i)
        {
            BaseVertex vertex = new Vertex();
            _vertex_list.add(vertex);
            _id_vertex_index.put(vertex.get_id(), vertex);
        }
        for(Way way: stores1) {
            add_edge(way.getFrom_loc(), way.getIn_loc(), way.getWay_length());
        }
    }

    protected void add_edge(int start_vertex_id, int end_vertex_id, double weight)
    {
        // на самом деле, мы должны убедиться, что идентификаторы всех вершин должны быть правильными.
        if(!_id_vertex_index.containsKey(start_vertex_id)
                || !_id_vertex_index.containsKey(end_vertex_id)
                || start_vertex_id == end_vertex_id)
        {
            throw new IllegalArgumentException("Ребро из "+start_vertex_id
                    +" в "+end_vertex_id+" не существует в этом графе.");
        }

        // обновить список смежных элементов графика
        Set<BaseVertex> fanout_vertex_set = new HashSet<BaseVertex>();
        if(_fanout_vertices_index.containsKey(start_vertex_id))
        {
            fanout_vertex_set = _fanout_vertices_index.get(start_vertex_id);
        }
        fanout_vertex_set.add(_id_vertex_index.get(end_vertex_id));
        _fanout_vertices_index.put(start_vertex_id, fanout_vertex_set);

        //
        Set<BaseVertex> fanin_vertex_set = new HashSet<BaseVertex>();
        if(_fanin_vertices_index.containsKey(end_vertex_id))
        {
            fanin_vertex_set = _fanin_vertices_index.get(end_vertex_id);
        }
        fanin_vertex_set.add(_id_vertex_index.get(start_vertex_id));
        _fanin_vertices_index.put(end_vertex_id, fanin_vertex_set);

        // сохранить новое ребро
        _vertex_pair_weight_index.put(
                new Pair<Integer, Integer>(start_vertex_id, end_vertex_id),
                weight);

        ++_edge_num;
    }


    public Set<BaseVertex> get_adjacent_vertices(BaseVertex vertex)
    {
        return _fanout_vertices_index.containsKey(vertex.get_id())
                ? _fanout_vertices_index.get(vertex.get_id())
                : new HashSet<BaseVertex>();
    }


    public Set<BaseVertex> get_precedent_vertices(BaseVertex vertex)
    {
        return _fanin_vertices_index.containsKey(vertex.get_id())
                ? _fanin_vertices_index.get(vertex.get_id())
                : new HashSet<BaseVertex>();
    }


    public double get_edge_weight(BaseVertex source, BaseVertex sink)
    {
        return _vertex_pair_weight_index.containsKey(
                new Pair<Integer, Integer>(source.get_id(), sink.get_id()))?
                _vertex_pair_weight_index.get(
                        new Pair<Integer, Integer>(source.get_id(), sink.get_id()))
                : DISCONNECTED;
    }

    public List<BaseVertex> get_vertex_list()
    {
        return _vertex_list;
    }

    public BaseVertex get_vertex(int id)
    {
        return _id_vertex_index.get(id);
    }

}


