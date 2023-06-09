package com.example.myroute.yen_algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class YenTopKShortestPathsAlg
{
    private VariableGraph _graph = null;

    // промежуточные переменные
    private List<Path> _result_list = new Vector<Path>();
    private Map<Path, BaseVertex> _path_derivation_vertex_index = new HashMap<Path, BaseVertex>();
    private QYPriorityQueue<Path> _path_candidates = new QYPriorityQueue<Path>();

    // конечные вершины путей
    private BaseVertex _source_vertex = null;
    private BaseVertex _target_vertex = null;

    // переменные для отладки и тестирования
    private int _generated_path_num = 0;


    public YenTopKShortestPathsAlg(BaseGraph graph)
    {
        this(graph, null, null);
    }

    public YenTopKShortestPathsAlg(BaseGraph graph,
                                   BaseVertex source_vt, BaseVertex target_vt)
    {
        if(graph == null)
        {
            throw new IllegalArgumentException("Нулевой объект graph!");
        }
        //
        _graph = new VariableGraph((Graph)graph);
        _source_vertex = source_vt;
        _target_vertex = target_vt;
        //
        _init();
    }

    //инициализация
    private void _init()
    {
        clear();
        // получаем кратчайший путь, если существуют исходная и целевая вершина
        if(_source_vertex != null && _target_vertex != null)
        {
            Path shortest_path = get_shortest_path(_source_vertex, _target_vertex);
            if(!shortest_path.get_vertices().isEmpty())
            {
                _path_candidates.add(shortest_path);
                _path_derivation_vertex_index.put(shortest_path, _source_vertex);
            }
        }
    }

    //очищение переменных класса
    public void clear()
    {
        _path_candidates = new QYPriorityQueue<Path>();
        _path_derivation_vertex_index.clear();
        _result_list.clear();
        _generated_path_num = 0;
    }

    //Поиск кратчайшего пути через алгоритм Дейкстры
    public Path get_shortest_path(BaseVertex source_vt, BaseVertex target_vt)
    {
        DijkstraShortestPathAlg dijkstra_alg = new DijkstraShortestPathAlg(_graph);
        return dijkstra_alg.get_shortest_path(source_vt, target_vt);
    }

    //Проверяем, существует ли путь, который является самым коротким среди всех кандидатов.
    public boolean has_next()
    {
        return !_path_candidates.isEmpty();
    }

    //Находим кратчайший путь среди всех, соединяющих источник с целью.
    public Path next()
    {
        //3.1 подготовка к удалению вершин и дуг
        Path cur_path = _path_candidates.poll();
        _result_list.add(cur_path);

        BaseVertex cur_derivation = _path_derivation_vertex_index.get(cur_path);
        int cur_path_hash =
                cur_path.get_vertices().subList(0, cur_path.get_vertices().indexOf(cur_derivation)).hashCode();

        int count = _result_list.size();

        //3.2 удаление вершины и дуги в графе
        for(int i=0; i<count-1; ++i)
        {
            Path cur_result_path = _result_list.get(i);

            int cur_dev_vertex_id =
                    cur_result_path.get_vertices().indexOf(cur_derivation);

            if(cur_dev_vertex_id < 0) continue;

            //Рассматриваем всех кандидатов
            int path_hash = cur_result_path.get_vertices().subList(0, cur_dev_vertex_id).hashCode();
            if(path_hash != cur_path_hash) continue;

            BaseVertex cur_succ_vertex =
                    cur_result_path.get_vertices().get(cur_dev_vertex_id+1);

            _graph.remove_edge(new Pair<Integer,Integer>(
                    cur_derivation.get_id(), cur_succ_vertex.get_id()));
        }

        int path_length = cur_path.get_vertices().size();
        List<BaseVertex> cur_path_vertex_list = cur_path.get_vertices();
        for(int i=0; i<path_length-1; ++i)
        {
            _graph.remove_vertex(cur_path_vertex_list.get(i).get_id());
            _graph.remove_edge(new Pair<Integer,Integer>(
                    cur_path_vertex_list.get(i).get_id(),
                    cur_path_vertex_list.get(i+1).get_id()));
        }

        //3.3 вычислить кратчайшее дерево с корнем в целевой вершине графика
        DijkstraShortestPathAlg reverse_tree = new DijkstraShortestPathAlg(_graph);
        reverse_tree.get_shortest_path_flower(_target_vertex);

        //3.4 восстановите удаленные вершины, обновите стоимость и определите новые результаты-кандидаты
        boolean is_done = false;
        for(int i=path_length-2; i>=0 && !is_done; --i)
        {
            //3.4.1 получить вершину для восстановления
            BaseVertex cur_recover_vertex = cur_path_vertex_list.get(i);
            _graph.recover_removed_vertex(cur_recover_vertex.get_id());

            //3.4.2 проверьте, следует ли нам прекратить продолжение на следующей итерации
            if(cur_recover_vertex.get_id() == cur_derivation.get_id())
            {
                is_done = true;
            }

            //3.4.3 рассчитать стоимость
            Path sub_path = reverse_tree.update_cost_forward(cur_recover_vertex);

            //3.4.4 получить один возможный результат, если это возможно
            if(sub_path != null)
            {
                ++_generated_path_num;

                //3.4.4.1 получить префикс из соответствующего пути
                double cost = 0;
                List<BaseVertex> pre_path_list = new Vector<BaseVertex>();
                reverse_tree.correct_cost_backward(cur_recover_vertex);

                for(int j=0; j<path_length; ++j)
                {
                    BaseVertex cur_vertex = cur_path_vertex_list.get(j);
                    if(cur_vertex.get_id() == cur_recover_vertex.get_id())
                    {
                        j=path_length;
                    }else
                    {
                        cost += _graph.get_edge_weight_of_graph(cur_path_vertex_list.get(j),
                                cur_path_vertex_list.get(j+1));
                        pre_path_list.add(cur_vertex);
                    }
                }
                pre_path_list.addAll(sub_path.get_vertices());

                //3.4.4.2 составить список кандидатов
                sub_path.set_weight(cost+sub_path.get_weight());
                sub_path.get_vertices().clear();
                sub_path.get_vertices().addAll(pre_path_list);

                //3.4.4.3 включить его в пул кандидатов, если он новый
                if(!_path_derivation_vertex_index.containsKey(sub_path))
                {
                    _path_candidates.add(sub_path);
                    _path_derivation_vertex_index.put(sub_path, cur_recover_vertex);
                }
            }

            //3.4.5 восстановить ребро
            BaseVertex succ_vertex = cur_path_vertex_list.get(i+1);
            _graph.recover_removed_edge(new Pair<Integer, Integer>(
                    cur_recover_vertex.get_id(), succ_vertex.get_id()));

            //3.4.6 обновить стоимость при необходимости
            double cost_1 = _graph.get_edge_weight(cur_recover_vertex, succ_vertex)
                    + reverse_tree.get_start_vertex_distance_index().get(succ_vertex);

            if(reverse_tree.get_start_vertex_distance_index().get(cur_recover_vertex) >  cost_1)
            {
                reverse_tree.get_start_vertex_distance_index().put(cur_recover_vertex, cost_1);
                reverse_tree.get_predecessor_index().put(cur_recover_vertex, succ_vertex);
                reverse_tree.correct_cost_backward(cur_recover_vertex);
            }
        }

        //3.5 восстановить все
        _graph.recover_removed_edges();
        _graph.recover_removed_vertices();

        //
        return cur_path;
    }

    //Получите топ-K кратчайших путей, соединяющих источник и цель.
    public List<Path> get_shortest_paths(BaseVertex source_vertex,
                                         BaseVertex target_vertex, int top_k)
    {
        _source_vertex = source_vertex;
        _target_vertex = target_vertex;

        _init();
        int count = 0;
        while(has_next() && count < top_k)
        {
            next();
            ++count;
        }

        return _result_list;
    }

    /*//Возвращает список результатов, сгенерированных в целом.
    public List<Path> get_result_list()
    {
        return _result_list;
    }

    //Количество различных кандидатов, сгенерированных в целом.
    public int get_cadidate_size()
    {
        return _path_derivation_vertex_index.size();
    }

    public int get_generated_path_size()
    {
        return _generated_path_num;
    }*/
}


