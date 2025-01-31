package mk.ukim.finki.eventguide.fetchData;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class LoadDataFromCsv {
    static public <T> List<T> loadObjectList(Class<T> type, String fileName) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
            CsvMapper mapper = new CsvMapper();

            File file = new File(fileName);
            MappingIterator<T> readValues =
                    mapper.readerFor(type).with(bootstrapSchema).readValues(file);

            return readValues.readAll();
        } catch (Exception e) {
            System.out.println("Error occurred while loading object list from file: " + fileName);
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
