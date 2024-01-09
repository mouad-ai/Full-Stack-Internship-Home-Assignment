import React, { useState } from 'react';

const perPage = 8;
const maxPages = 3;

const ProcessingResults = ({ employees, jobSummary }) => {
    const [currentPage, setCurrentPage] = useState(1);

    const lastIndex = currentPage * perPage;
    const firstIndex = lastIndex - perPage;
    const currentEmployees = employees.slice(firstIndex, lastIndex);

    const totalPages = Math.ceil(employees.length / perPage);

    const visiblePages = () => {
        const pages = [];
        let start, end;

        if (totalPages <= maxPages) {
            start = 1;
            end = totalPages;
        } else {
            const halfMax = Math.floor(maxPages / 2);

            if (currentPage <= halfMax) {
                start = 1;
                end = maxPages;
            } else if (currentPage + halfMax >= totalPages) {
                start = totalPages - maxPages + 1;
                end = totalPages;
            } else {
                start = currentPage - halfMax;
                end = currentPage + halfMax;
            }
        }

        for (let i = start; i <= end; i++) {
            pages.push(i);
        }

        return pages;
    };

    const handlePageChange = (pageNumber) => {
        if (pageNumber >= 1 && pageNumber <= totalPages) {
            setCurrentPage(pageNumber);
        }
    };

    return (
        <div>
            <h2>Employee Information</h2>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Job </th>
                    <th>Salary</th>
                </tr>
                </thead>
                <tbody>
                {currentEmployees.map((employee) => (
                    <tr key={employee.id}>
                        <td>{employee.id}</td>
                        <td>{employee.name}</td>
                        <td>{employee.job}</td>
                        <td>{employee.salary}</td>
                    </tr>
                ))}
                </tbody>
            </table>

            <div className="pagination">
                <ul>
                    <li>
                        <a
                            href="#!"
                            onClick={() => handlePageChange(currentPage - 1)}
                            className={currentPage === 1 ? 'disabled' : ''}
                        >
                             Prev
                        </a>
                    </li>
                    {visiblePages().map((pageNumber) => (
                        <li key={pageNumber} className={pageNumber === currentPage ? 'active' : ''}>
                            <a href="#!" onClick={() => handlePageChange(pageNumber)}>
                                {pageNumber}
                            </a>
                        </li>
                    ))}
                    <li>
                        <a
                            href="#!"
                            onClick={() => handlePageChange(currentPage + 1)}
                            className={currentPage === totalPages ? 'disabled' : ''}
                        >
                            Next
                        </a>
                    </li>
                </ul>
            </div>

            <h2>Jobs Summary</h2>
            <table>
                <thead>
                <tr>
                    <th>Job</th>
                    <th>Average Salary</th>
                </tr>
                </thead>
                <tbody>
                {Object.entries(jobSummary).map(([job, avgSalary]) => (
                    <tr key={job}>
                        <td>{job}</td>
                        <td>{avgSalary}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default ProcessingResults;
