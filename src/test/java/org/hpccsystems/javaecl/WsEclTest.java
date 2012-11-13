package org.hpccsystems.javaecl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.hpccsystems.javaecl.Column;
import org.hpccsystems.javaecl.WsEcl;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author chalaax
 */
public class WsEclTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void must_execute_successfully() {
        // temporary!
        thrown.expect(UnsupportedOperationException.class);
        
        throw new UnsupportedOperationException("Integration test - not ready yet");
//        WsEcl wsEcl = new WsEcl("192.168.59.129", "thor", "personandcontact.1");
//        wsEcl.execute();
    }
    
    @Ignore
    @Test
    public void must_parse_successfully() throws Exception {
        List<List<List<Column>>> expected = new ArrayList<List<List<Column>>>();
        
        String buf = "<Dataset><Row><name></name><value></value></Row></Dataset>";
        InputStream xml = new ByteArrayInputStream(buf.getBytes());
        List<List<List<Column>>> actual = WsEcl.parse(xml);
        
        assertEquals(expected, actual);
    }
}
