
import java.io.*;
import java.util.Map;
import java.util.Scanner;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class FileReaderSpout implements IRichSpout {
    private SpoutOutputCollector _collector;
    private TopologyContext context;
    private String file;
    BufferedReader br = null;

    public FileReaderSpout(final String file) {
        this.file = file;
    }

    @Override
    public void open(Map conf, TopologyContext context,
                     SpoutOutputCollector collector) {

        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.context = context;
        this._collector = collector;
    }

    @Override
    public void nextTuple() {
        Utils.sleep(100);
        String sCurrentLine = null;

        try {
            sCurrentLine = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (sCurrentLine != null) {
            _collector.emit(new Values(sCurrentLine));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("word"));

    }

    @Override
    public void close() {
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }

    @Override
    public void ack(Object msgId) {
    }

    @Override
    public void fail(Object msgId) {
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
