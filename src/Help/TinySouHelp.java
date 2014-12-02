package Help;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tinysou on 14-9-22.
 * Author:Yeming Wang
 * Data: 2014.10.11
 * 简介：微搜索Json类，用于Gson处理,已废弃不再使用
 */
public class TinySouHelp {
    public INFO info;
    public List<RECORDS> records = new ArrayList<RECORDS>();
    public ERRORS errors;

    public class INFO {
        public String query = null;
        public String page = null;
        public String per_page = null;
        public String total = null;
        public String max_score = null;
    }

    public class RECORDS {
        public String collection = null;
        public String score = null;
        public HIGHLIGHT highlight = null;
        public DOCUMENT document = null;
    }

    public class HIGHLIGHT {
        public List<String> body = new ArrayList<String>();
        public List<String> title = new ArrayList<String>();
        public List<String> sections = new ArrayList<String>();
    }

    public class DOCUMENT {
        public String id = null;
        public String title = null;
        public List<String> tags = new ArrayList<String>();
        public String author = null;
        public String updated_at = null;
        public String body = null;
        public List<String> sections = new ArrayList<String>();
        public String url = null;
    }

    public class ERRORS {
        public List<String> search_fields = new ArrayList<String>();
    }

    public void TinySouHelp() {
    }
}
