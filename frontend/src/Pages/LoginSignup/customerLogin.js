import React, { useContext } from 'react';
import styles from './style.module.scss';
import { UserContext } from '../Auth/UserContext';
import { useNavigate } from 'react-router-dom';

import Navbar from '../../Components/Navbar'

const CustomerLogin = () => {
	const { loginAsCustomer } = useContext(UserContext);
	const navigate = useNavigate();

	const fetchVendorFromEmail = async (email) => {
        try {
            const response = await fetch(`http://localhost:8080/api/customers/email/${email}`);
            if (response.status === 500) {
                // alert('Server error. Please try again later.');
                return null;
            }
            const customer = await response.json();
            return customer;
        } catch (error) {
            console.error('Error fetching vendor:', error);
            return null;
        }
    };

	const handleLogin = () => {

		const emailInput = document.querySelector('input[type="text"]');
        const passwordInput = document.querySelector('input[type="password"]');

        fetchVendorFromEmail(emailInput.value).then((customer) => {
            if (customer) {
                const validPassword = passwordInput.value === customer.password;
        
                if (!validPassword) {
                    alert('Invalid password');
                    return;
                }

                loginAsCustomer(customer.id);
                navigate('/profile');
            } else {
                alert('Customer not found');
            } 
        });		
	};

	return (
		<div>
			<Navbar />
			<div className={styles.container}>
				<div className={styles.loginCard}>
					<h2>Customer Login</h2>
					<form onSubmit={(e) => e.preventDefault()}>
						<input type="text" placeholder="Username" />
						<input type="password" placeholder="Password" />
						<button onClick={handleLogin}>Login</button>
					</form>
					<p>Don't have an account? <a href="/customer/signup">Sign up as customer</a></p>
				</div>
			</div>
		</div>
	);
};

export default CustomerLogin;