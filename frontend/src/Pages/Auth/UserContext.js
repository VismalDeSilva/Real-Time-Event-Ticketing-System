import React, { createContext, useEffect, useState } from 'react';

const UserContext = createContext();

const UserProvider = ({ children }) => {
  // const [userRole, setUserRole] = useState(null);
  const [userRole, setUserRole] = useState(() => {
    const storedUserRole = localStorage.getItem('userRole');
    return storedUserRole ? storedUserRole : null;
  });

  const [userId, setUserId] = useState(() => {
    const storedUserId = localStorage.getItem('userId');
    return storedUserId ? storedUserId : null;
  });

  useEffect(() => {
    localStorage.setItem('userRole', userRole);
    console.log("User Role is set",userRole);
    localStorage.setItem('userId', userId);
    console.log("User Id is set",userId);
  }, [userRole, userId]);

  const loginAsCustomer = (userId) => {
    setUserRole('customer');
    setUserId(userId);
    console.log('Login as customer', userRole);
  };

  const loginAsVendor = (userId) => {
    setUserRole('vendor');
    setUserId(userId);
    console.log('Login as vendor', userRole);
  };

  const logout = () => {
    setUserRole(null);
    setUserId(null);
    localStorage.removeItem('userRole');
    localStorage.removeItem('userId');
  };

  return (
    <UserContext.Provider value={{ userRole, loginAsCustomer, loginAsVendor, logout, userId }}>
      {children}
    </UserContext.Provider>
  );
};

export { UserProvider, UserContext };