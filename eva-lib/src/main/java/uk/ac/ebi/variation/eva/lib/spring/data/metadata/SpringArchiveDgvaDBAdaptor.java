package uk.ac.ebi.variation.eva.lib.spring.data.metadata;

import org.opencb.datastore.core.QueryOptions;
import org.opencb.datastore.core.QueryResult;
import org.opencb.opencga.storage.core.adaptors.ArchiveDBAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import uk.ac.ebi.variation.eva.lib.spring.data.repository.DGvaStudyBrowserRepository;

import javax.persistence.Tuple;
import java.util.*;

import static uk.ac.ebi.variation.eva.lib.spring.data.utils.DgvaDBUtils.getSpeciesAndTypeFilters;

/**
 * Created by jorizci on 28/09/16.
 */
public class SpringArchiveDgvaDBAdaptor implements ArchiveDBAdaptor {

    @Autowired
    private DGvaStudyBrowserRepository dgvaStudyBrowserRepository;

    @Override
    public QueryResult countStudies() {
        long start = System.currentTimeMillis();
        long count = dgvaStudyBrowserRepository.count();
        long end = System.currentTimeMillis();
        return new QueryResult(null, ((Long) (end - start)).intValue(), 1, 1, null, null, Arrays.asList(count));
    }

    @Override
    public QueryResult countStudiesPerSpecies(QueryOptions queryOptions) {
        long start = System.currentTimeMillis();
        Specification filterSpecification = getSpeciesAndTypeFilters(queryOptions);
        List<Tuple> countGroupBy = dgvaStudyBrowserRepository.groupCount(DGvaStudyBrowserRepository.COMMON_NAME, filterSpecification, false);
        List<Map.Entry<String, Long>> result = new ArrayList<>();
        for (Tuple tuple : countGroupBy) {
            String species = tuple.get(0) != null ? (String) tuple.get(0) : "Others";
            long count = (long) tuple.get(1);
            result.add(new AbstractMap.SimpleEntry<>(species, count));
        }
        long end = System.currentTimeMillis();
        return new QueryResult(null, ((Long) (end - start)).intValue(), result.size(), result.size(), null, null, result);
    }

    @Override
    public QueryResult countStudiesPerType(QueryOptions queryOptions) {
        long start = System.currentTimeMillis();
        Specification filterSpecification = getSpeciesAndTypeFilters(queryOptions);
        List<Tuple> countGroupBy = dgvaStudyBrowserRepository.groupCount(DGvaStudyBrowserRepository.STUDY_TYPE, filterSpecification, false);
        List<Map.Entry<String, Long>> result = new ArrayList<>();
        for (Tuple tuple : countGroupBy) {
            String species = tuple.get(0) != null ? (String) tuple.get(0) : "Others";
            long count = (long) tuple.get(1);
            result.add(new AbstractMap.SimpleEntry<>(species, count));
        }
        long end = System.currentTimeMillis();
        return new QueryResult(null, ((Long) (end - start)).intValue(), result.size(), result.size(), null, null, result);
    }

    @Override
    public QueryResult countFiles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public QueryResult countSpecies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public QueryResult getSpecies(String s, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
