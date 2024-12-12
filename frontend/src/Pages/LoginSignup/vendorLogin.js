// src/Pages/VendorLogin/index.js
import React, { useContext } from 'react';
import styles from './style.module.scss';
import { UserContext } from '../Auth/UserContext';
import { useNavigate } from 'react-router-dom';

import Navbar from '../../Components/Navbar'

const VendorLogin = () => {
    const { loginAsVendor } = useContext(UserContext);
    const navigate = useNavigate();

    const fetchVendorFromEmail = async (email) => {
        try {
            const response = await fetch(`http://localhost:8080/api/vendors/email/${email}`);
            if (response.status === 500) {
                // alert('Server error. Please try again later.');
                return null;
            }
            const vendor = await response.json();
            return vendor;
        } catch (error) {
            console.error('Error fetching vendor:', error);
            return null;
        }
    };

    const handleLogin = () => {

        const emailInput = document.querySelector('input[type="text"]');
        const passwordInput = document.querySelector('input[type="password"]');

        fetchVendorFromEmail(emailInput.value).then((vendor) => {
            if (vendor) {
                const validPassword = passwordInput.value === vendor.password;
        
                if (!validPassword) {
                    alert('Invalid password');
                    return;
                }

                loginAsVendor(vendor.id);
                navigate('/profile');
            } else {
                alert('Vendor not found');
            } 
        });
    };

    return (
        <div>
            <Navbar />
            <div className={styles.container}>
                <div className={styles.loginCard}>
                    <h2>Vendor Login</h2>
                    <form onSubmit={(e) => e.preventDefault()}>
                        <input type="text" placeholder="Email" />
                        <input type="password" placeholder="Password" />
                        <button onClick={handleLogin}>Login</button>
                    </form>
                    <p>Don't have an account? <a href="/vendor/signup">Sign up as a Vendor</a></p>
                </div>
            </div>
        </div>
    );
};

export default VendorLogin;