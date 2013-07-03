/*
 * ModeShape (http://www.modeshape.org)
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * See the AUTHORS.txt file in the distribution for a full listing of 
 * individual contributors.
 *
 * ModeShape is free software. Unless otherwise indicated, all code in ModeShape
 * is licensed to you under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 * 
 * ModeShape is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.modeshape.jcr.security.acl;

import java.util.ArrayList;
import javax.jcr.RepositoryException;
import javax.jcr.security.AccessControlEntry;
import javax.jcr.security.AccessControlException;
import javax.jcr.security.AccessControlList;
import javax.jcr.security.Privilege;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the AccessControlPolicyIterator implementation.
 * 
 * @author kulikov
 */
public class AccessControlPolicyIteratorImplTest {

    private AccessControlPolicyIteratorImpl it;
    
    public AccessControlPolicyIteratorImplTest() {
    }
    
    @Before
    public void setUp() throws AccessControlException, RepositoryException {
        ArrayList<JcrAccessControlList> list = new ArrayList();
        
        //acl-1
        JcrAccessControlList alice = new JcrAccessControlList();
        alice.addAccessControlEntry(new PrincipalImpl("alice"), new Privilege[]{});
        
        
        JcrAccessControlList bob = new JcrAccessControlList();
        bob.addAccessControlEntry(new PrincipalImpl("bob"), new Privilege[]{});
     
        
        it = new AccessControlPolicyIteratorImpl(alice, bob);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of nextAccessControlPolicy method, of class AccessControlPolicyIteratorImpl.
     */
    @Test
    public void testNextAccessControlPolicy() throws RepositoryException {
        AccessControlList acl = (AccessControlList) it.nextAccessControlPolicy();
        AccessControlEntry[] entries = acl.getAccessControlEntries();
        assertEquals(1, entries.length);
    }

    /**
     * Test of skip method, of class AccessControlPolicyIteratorImpl.
     */
    @Test
    public void testSkip() {
        assertTrue(it.hasNext());
        
        it.skip(1);
        assertTrue(it.hasNext());

        it.skip(1);
        assertFalse(it.hasNext());
    }

    /**
     * Test of getSize method, of class AccessControlPolicyIteratorImpl.
     */
    @Test
    public void testGetSize() {
        assertEquals(2, it.getSize());
        it.remove();
        assertEquals(1, it.getSize());
    }

    /**
     * Test of getPosition method, of class AccessControlPolicyIteratorImpl.
     */
    @Test
    public void testGetPosition() {
        assertEquals(0, it.getPosition());
        it.skip(1);
        
        assertEquals(1, it.getPosition());
    }

    /**
     * Test of hasNext method, of class AccessControlPolicyIteratorImpl.
     */
    @Test
    public void testHasNext() {
        assertTrue(it.hasNext());
        
        it.skip(1);
        assertTrue(it.hasNext());

        it.skip(1);
        assertFalse(it.hasNext());
    }

    /**
     * Test of next method, of class AccessControlPolicyIteratorImpl.
     */
    @Test
    public void testNext() throws RepositoryException {
        AccessControlList acl = (AccessControlList) it.next();
        AccessControlEntry[] entries = acl.getAccessControlEntries();
        assertEquals("alice", entries[0].getPrincipal().getName());

        acl = (AccessControlList) it.next();
        entries = acl.getAccessControlEntries();
        assertEquals("bob", entries[0].getPrincipal().getName());
    }

    /**
     * Test of remove method, of class AccessControlPolicyIteratorImpl.
     */
    @Test
    public void testRemove() {
        assertEquals(2, it.getSize());
        it.remove();
        assertEquals(1, it.getSize());
    }
}